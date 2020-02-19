package br.com.menu.menudigital.restaurant;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.management.BadAttributeValueExpException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
import br.com.menu.menudigital.restaurantapprovalrequest.RestaurantApprovalRequest;
import br.com.menu.menudigital.restaurantapprovalrequest.RestaurantApprovalRequestRepository;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import br.com.menu.menudigital.user.UserService;
import br.com.menu.menudigital.utils.EmailSender;
import br.com.menu.menudigital.utils.exception.PaymentRequiredException;
import br.com.menu.menudigital.utils.exception.ResourceAlreadyExistsException;
import javassist.NotFoundException;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private UserRepository userRepository;
	private MenuRepository menuRepository;
	private CategoryRepository categoryRepository;
	private RestaurantService restaurantService;
	private EmailSender emailSender;
	private RestaurantApprovalRequestRepository restaurantApprovalRequest;
	private UserService userService;

    @Value("${support.emails}")
    private String[] supportEmails;

	public RestaurantController(RestaurantRepository restaurantRepository, UserRepository userRepository,
			MenuRepository menuRepository, CategoryRepository categoryRepository, RestaurantService restaurantService,
			EmailSender emailSender, RestaurantApprovalRequestRepository restaurantApprovalRequest,
			UserService userService, String[] supportEmails) {
		super();
		this.restaurantRepository = restaurantRepository;
		this.userRepository = userRepository;
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.restaurantService = restaurantService;
		this.emailSender = emailSender;
		this.restaurantApprovalRequest = restaurantApprovalRequest;
		this.userService = userService;
		this.supportEmails = supportEmails;
	}

	@GetMapping
	public @ResponseBody List<Restaurant> getAllRestaurants(@RequestParam(name="city", required=false) String city, @RequestParam(name="name", required=false) String name) throws BadAttributeValueExpException {
		if(city == null && name == null) {
			throw new BadAttributeValueExpException("Voce precisa especificar ao menos um filtro");
		}
		
		if(city != null && name != null) {
			String cityLike = "%" + city + "%";
			String nameLike = "%" + name + "%";

			return restaurantRepository.findByApprovedAndCityAndNameLike(true, cityLike, nameLike);
			
		} else if (city != null) {
			return restaurantRepository.findByApprovedAndCityIgnoreCaseContaining(true, city);
		} else {
			return restaurantRepository.findByApprovedAndNameIgnoreCaseContaining(true, name);
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
			@RequestParam(name = "file", required = false) MultipartFile file, Principal principal) throws IOException, PaymentRequiredException {
		
		User user = userRepository.findByEmail(principal.getName());
		
		boolean isMax = userService.hasMaxRestaurantsForPlan(user);
		
		if(isMax) {
			throw new PaymentRequiredException("Atingiu o limite maximo de restaurantes para este plano.");
		}
		return restaurantService.saveRestaurant(newRestaurantDTO, file, user);
	}

	
	@GetMapping("/{id}/approvalRequest")
	public @ResponseBody RestaurantApprovalRequest getApprovalRequest(@PathVariable Long id) {
		return restaurantApprovalRequest.findByRestaurantId(id);
		
	}
	
	@PostMapping("/{id}/approvalRequest")
	public @ResponseBody ResponseEntity<String> approvalRequest(@PathVariable Long id) throws MessagingException {
		RestaurantApprovalRequest request = restaurantApprovalRequest.findByRestaurantId(id);
		
		if(request != null) {
			return ResponseEntity.ok("Solicitacao ja foi enviada e esta em analise. Aguarde!");
		}
		
		restaurantApprovalRequest.save(new RestaurantApprovalRequest(id));
		StringBuilder emailBody = new StringBuilder("Solicitacao de aprovacao para o restaurante de id: " + id);
		
		try {
			emailSender.sendEmail(supportEmails, "EatUp - Aprovacao de restaurante", emailBody.toString());
		} catch (MessagingException e) {
			throw new MessagingException("Erro ao enviar o email");
		}
		return ResponseEntity.ok("Email enviado com sucesso!");
		
	}
	
	@PostMapping("/{id}/approve")
	public @ResponseBody ResponseEntity<String> approve(@PathVariable Long id) throws MessagingException, ResourceAlreadyExistsException {
		RestaurantApprovalRequest request = restaurantApprovalRequest.findByRestaurantId(id);
		
		if(request == null) {
			throw new ResourceAlreadyExistsException("Nao existe nenhuma solicitacao com este id.");
		}

		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));
		
		restaurantApprovalRequest.delete(request);
		restaurant.setApproved(true);
		restaurantRepository.save(restaurant);
		
		return ResponseEntity.ok("Restaurante aprovado!");
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
			@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
		Restaurant restaurant = restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));

		return restaurantService.updateRestaurantImage(file, restaurant);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody void deleteRestaurant(@PathVariable("id") Long restaurantId) throws NotFoundException {
		Restaurant restaurant = restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new EntityNotFoundException("Nao existe restaurante com este id."));
		restaurantService.softDeleteRestaurant(restaurant);
	}
}
