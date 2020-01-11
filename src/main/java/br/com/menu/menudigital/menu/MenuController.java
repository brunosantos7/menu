package br.com.menu.menudigital.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuRepository menuRepository;

	public MenuController(MenuRepository menuRepository) {
		super();
		this.menuRepository = menuRepository;
	}
	
	@GetMapping
	public @ResponseBody Iterable<Menu> getAllMenus() {
		return menuRepository.findAll();
	}
	
	@PostMapping("/save")
	public @ResponseBody String saveMenu() {
		Menu menu = new Menu();
		menu.setTitle("Title");
		menu.setRestaurantId(1l);
		menuRepository.save(menu);
		return "true";
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody String deleteMenu(@PathVariable("id") Long menuId) throws Exception {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new Exception("Does not exist menu with this id."));
		menuRepository.delete(menu);
		return "true";
	}
	
	
}
