package br.com.menu.menudigital.menu;

import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.menu.menudigital.category.Category;
import br.com.menu.menudigital.category.CategoryRepository;
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import br.com.menu.menudigital.user.UserService;
import br.com.menu.menudigital.utils.exception.PaymentRequiredException;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuRepository menuRepository;
	private CategoryRepository categoryRepository;
	private MenuService menuService;
	private UserRepository userRepository;
	private UserService userService;

	public MenuController(MenuRepository menuRepository, CategoryRepository categoryRepository, MenuService menuService,
			UserRepository userRepository, UserService userService) {
		super();
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.menuService = menuService;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public @ResponseBody Menu getMenuById(@PathVariable Long id) {
		return menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe menu com este id."));
	}

	@GetMapping("/{id}/categories")
	public @ResponseBody List<Category> getMenuCategoriesByMenuId(@PathVariable Long id) {
		return categoryRepository.findByMenuId(id);
	}

	@PostMapping
	public @ResponseBody Menu save(@RequestBody MenuDTO newMenuDTO, Principal principal) throws PaymentRequiredException {
		User user = userRepository.findByEmail(principal.getName());
		
		boolean isMax = userService.hasMaxMenusForPlan(user);
		
		if(isMax) {
			throw new PaymentRequiredException("Atingiu o limite maximo de menus para este plano.");
		}
		
		return menuRepository.save(newMenuDTO.toMenuEntity());
	}

	@PutMapping("/{id}")
	public @ResponseBody Menu update(@PathVariable Long id, @RequestBody MenuDTO newMenuDTO) {
		Menu menu = menuRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe menu com este id."));

		menu.setRestaurantId(newMenuDTO.getRestaurantId());
		menu.setTitle(newMenuDTO.getTitle());

		return menuRepository.save(menu);
	}

	@DeleteMapping("/{id}")
	public @ResponseBody void delete(@PathVariable("id") Long menuId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Nao existe menu com este id."));
		menuService.softDeleteMenu(menu);
	}

}
