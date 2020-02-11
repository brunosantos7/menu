package br.com.menu.menudigital.category;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.menu.menudigital.product.Product;
import br.com.menu.menudigital.product.ProductRepository;
import br.com.menu.menudigital.product.ProductService;

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

}
