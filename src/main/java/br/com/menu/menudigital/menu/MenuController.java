package br.com.menu.menudigital.menu;

import java.util.List;
import java.util.Optional;

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
import javassist.NotFoundException;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuRepository menuRepository;

	private CategoryRepository categoryRepository;

	public MenuController(MenuRepository menuRepository, CategoryRepository categoryRepository) {
		super();
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/{id}")
	public @ResponseBody Menu getMenuById(@PathVariable Long id) throws NotFoundException {
		return menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist menu with this id"));
	}
	
	@GetMapping("/{id}/categories")
	public @ResponseBody List<Category> getMenuCategoriesById(@PathVariable Long id) {
		return categoryRepository.findByMenuId(id);
	}
	
	@PostMapping
	public @ResponseBody Menu saveMenu(@RequestBody MenuDTO newMenuDTO) {
		return menuRepository.save(newMenuDTO.toMenuEntity());
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Menu updateMenu(@PathVariable Long id, @RequestBody MenuDTO newMenuDTO) throws NotFoundException {
		Menu menu = menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist menu with this id."));
		
		menu.setRestaurantProfileId(newMenuDTO.getRestaurantProfileId());
		menu.setTitle(newMenuDTO.getTitle());
		
		return menuRepository.save(menu);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody String deleteMenu(@PathVariable("id") Long menuId) throws NotFoundException {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("Does not exist menu with this id."));
		menuRepository.delete(menu);
		return "true";
	}
	
	
}
