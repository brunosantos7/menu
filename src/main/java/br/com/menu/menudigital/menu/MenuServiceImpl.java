package br.com.menu.menudigital.menu;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.menu.menudigital.category.Category;
import br.com.menu.menudigital.category.CategoryRepository;
import br.com.menu.menudigital.category.CategoryService;

@Service
public class MenuServiceImpl implements MenuService {

	private MenuRepository menuRepository;
	private CategoryRepository categoryRepository;
	private CategoryService categoryService;
	
	public MenuServiceImpl(MenuRepository menuRepository, CategoryRepository categoryRepository,
			CategoryService categoryService) {
		super();
		this.menuRepository = menuRepository;
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}

	public boolean softDeleteMenu(Menu menu) {
		
		List<Category> categories = categoryRepository.findByMenuId(menu.getId());
		for (Category category : categories) {
			categoryService.softDeleteCategory(category);
		}
		
		menu.setDeleted(true);
		menuRepository.save(menu);
		
		return true;
	}
}
