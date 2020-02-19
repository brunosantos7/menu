package br.com.menu.menudigital.category;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.product.Product;
import br.com.menu.menudigital.product.ProductRepository;
import br.com.menu.menudigital.product.ProductService;
import br.com.menu.menudigital.utils.file.FileUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private ProductRepository productRepository;
	private ProductService productService;
	private CategoryRepository categoryRepository;
	
	public CategoryServiceImpl(ProductRepository productRepository, ProductService productService,
			CategoryRepository categoryRepository) {
		super();
		this.productRepository = productRepository;
		this.productService = productService;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public boolean softDeleteCategory(Category category) {
		List<Product> products = productRepository.findByCategoryId(category.getId());
		
		for (Product product : products) {
			productService.softDeleteProduct(product);
		}
		
		category.setDeleted(true);
		categoryRepository.save(category);
		
		return true;
	}

	public Category saveCategory(CategoryDTO categoryDTO, MultipartFile file) throws IOException {
		Category category = categoryRepository.save(categoryDTO.toEntity());
		
		if(file != null) {

			String path = FileUtils.saveOnDisk(file, category.getRestaurantId());
	        category.setImagePath(path);
	        
	        return categoryRepository.save(category);
		}
		return category;
	}


	@Override
	public Category updateCategoryImage(MultipartFile file, Category category) throws IOException {
		
		String newPath = null;
		
		if(category.getImagePath() != null) {
			FileUtils.deleteFromDisk(category.getImagePath());
		}
		
		if(file != null) {
			newPath = FileUtils.saveOnDisk(file, category.getRestaurantId());
		}

		category.setImagePath(newPath);
		return categoryRepository.save(category);
	}

}
