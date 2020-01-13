package br.com.menu.menudigital.menu;

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

@Controller
@RequestMapping("/menu")
public class MenuController {

	private MenuRepository menuRepository;

	public MenuController(MenuRepository menuRepository) {
		super();
		this.menuRepository = menuRepository;
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Optional<Menu> getMenuById(@PathVariable Long id) throws Exception {
		return menuRepository.findById(id);
	}
	
	@PostMapping
	public @ResponseBody Menu saveMenu(@RequestBody MenuDTO newMenuDTO) {
		return menuRepository.save(newMenuDTO.toMenuEntity());
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Menu updateMenu(@PathVariable Long id, @RequestBody MenuDTO newMenuDTO) throws Exception {
		Menu menu = menuRepository.findById(id).orElseThrow(() -> new Exception("Does not exist menu with this id."));
		
		menu.setRestaurantId(newMenuDTO.getRestaurantId());
		menu.setTitle(newMenuDTO.getTitle());
		
		return menuRepository.save(menu);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody String deleteMenu(@PathVariable("id") Long menuId) throws Exception {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new Exception("Does not exist menu with this id."));
		menuRepository.delete(menu);
		return "true";
	}
	
	
}
