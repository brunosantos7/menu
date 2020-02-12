package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.category.Category;
import br.com.menu.menudigital.category.CategoryRepository;
import br.com.menu.menudigital.menu.Menu;
import br.com.menu.menudigital.menu.MenuRepository;
import br.com.menu.menudigital.restaurantapprovementrequest.RestaurantAppovementRequest;
import br.com.menu.menudigital.restaurantapprovementrequest.RestaurantAppovementRequestRepository;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurant;
import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;
import br.com.menu.menudigital.utils.EmailSender;
import javassist.NotFoundException;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private UserRepository userRepository;
	private UserHasRestaurantRepository userHasRestaurantRepository;
	private MenuRepository menuRepository;
	private CategoryRepository categoryRepository;
	private RestaurantService restaurantService;
	private EmailSender emailSender;
	private RestaurantAppovementRequestRepository restaurantAppovementRequestRepository;

    @Value("${support.emails}")
    private String[] supportEmails;

	public RestaurantController(RestaurantRepository restaurantRepository, UserRepository userRepository,
			UserHasRestaurantRepository userHasRestaurantRepository, MenuRepository menuRepository,
			CategoryRepository categoryRepository, RestaurantService restaurantService, EmailSender emailSender, RestaurantAppovementRequestRepository restaurantAppovementRequestRepository) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
		this.userHasRestaurantRepository = userHasRestaurantRepository;
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
		this.emailSender = emailSender;
		this.restaurantAppovementRequestRepository = restaurantAppovementRequestRepository;
	}

	@GetMapping
	public @ResponseBody List<Restaurant> getAllRestaurants(@RequestParam(name="city", required=false) String city, @RequestParam(name="name", required=false) String name) {
		if(city != null && name != null) {
			String cityLike = "%" + city + "%";
			String nameLike = "%" + name + "%";

			return restaurantRepository.findByCityAndNameLike(cityLike, nameLike);
			
		} else if (city != null) {
			return restaurantRepository.findByCityIgnoreCaseContaining(city);
		} else {
			return restaurantRepository.findByNameIgnoreCaseContaining(name);
		}
	}

	@GetMapping("/citiesAndStatesAvailable")
	public @ResponseBody Map<String, List<CityToStateAvailableDTO>> getAllCitiesAvailableByState() {
		List<CityToStateAvailableDTO> stateToCities = restaurantRepository.findAllCitiesAndSateAvailable();

		return stateToCities.stream().collect(Collectors.groupingBy(CityToStateAvailableDTO::getState));

	}

	@GetMapping("/{id}")
	public @ResponseBody Restaurant getRestaurantById(@PathVariable Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));
	}

	@GetMapping("/{id}/menus")
	public @ResponseBody List<Menu> getRestaurantMenus(@PathVariable Long id) {
		return menuRepository.findByRestaurantId(id);
	}

	@GetMapping("/{id}/products")
	public @ResponseBody List<Category> getRestaurantProducts(@PathVariable Long id) {
		return categoryRepository.findByRestaurantIdWithProducts(id);
	}

	@PostMapping
	public @ResponseBody Restaurant save(@Valid RestaurantDTO newRestaurantDTO,
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) throws IOException {
		User user = userRepository.findByEmail(principal.getName());

		Restaurant newRes = restaurantRepository.save(newRestaurantDTO.toRestaurantEntity());

		UserHasRestaurant relationship = new UserHasRestaurant();
		relationship.setUserId(user.getId());
		relationship.setRestaurantId(newRes.getId());

		userHasRestaurantRepository.save(relationship);

		if (file != null) {
			Path path = Paths.get(String.format("images/restaurant/%s", newRes.getId()));

			try {
				Files.createDirectories(path);

				String filename = StringUtils.cleanPath(file.getOriginalFilename());

				Files.copy(file.getInputStream(), path.resolve(filename));
				newRes.setImagePath(path.resolve(filename).toString());

			} catch (IOException e) {
				throw new IOException("Erro ao salvar imagem no disco.", e);
			}

			return restaurantRepository.save(newRes);
		}

		return newRes;
	}
	
	@GetMapping("/{id}/approvementRequest")
	public @ResponseBody RestaurantAppovementRequest getApprovementRequest(@RequestParam Long id) throws MessagingException {
		return restaurantAppovementRequestRepository.findByRestaurantId(id);
		
	}
	
	@PostMapping("/{id}/approvementRequest")
	public @ResponseBody ResponseEntity<String> approvementRequest(@RequestParam Long id) throws MessagingException {
		RestaurantAppovementRequest request = restaurantAppovementRequestRepository.findByRestaurantId(id);
		
		if(request != null) {
			return ResponseEntity.ok("Solicitacao ja foi enviada e esta em analise. Aguarde!");
		}
		
		restaurantAppovementRequestRepository.save(new RestaurantAppovementRequest(id));
		StringBuilder emailBody = new StringBuilder("Solicitacao de aprovacao para o restaurante de id: " + id);
		
		try {
			emailSender.sendEmail(supportEmails, "Recupecacao de senha", emailBody.toString());
		} catch (MessagingException e) {
			throw new MessagingException("Erro ao enviar o email");
		}
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}

	@PutMapping("/{id}")
	public @ResponseBody Restaurant updateRestaurant(@PathVariable Long id, @Valid RestaurantDTO newRestaurantDTO,
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));

		Restaurant restaurantEntity = newRestaurantDTO.toRestaurantEntity();
		restaurantEntity.setImagePath(restaurant.getImagePath());
		restaurantEntity.setId(restaurant.getId());

		return restaurantRepository.save(restaurantEntity);
	}

	@PutMapping("/{id}/image")
	public @ResponseBody Restaurant updateRestaurantImage(@PathVariable Long id,
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) throws IOException {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));

		Path path = Paths.get(String.format("images/restaurant/%s", restaurant.getId()));

		if (file != null) {

			if (path.toFile().exists()) {
				FileSystemUtils.deleteRecursively(path.toFile());
			}

			try {
				Files.createDirectories(path);

				String filename = StringUtils.cleanPath(file.getOriginalFilename());

				Files.copy(file.getInputStream(), path.resolve(filename));
				restaurant.setImagePath(path.resolve(filename).toString());

			} catch (IOException e) {
				throw new IOException("Erro ao salvar imagem no disco.", e);
			}

		} else {
			FileSystemUtils.deleteRecursively(path.toFile());
			restaurant.setImagePath(null);
		}

		return restaurantRepository.save(restaurant);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));
		restaurantService.softDeleteRestaurant(restaurant);
	}
}
