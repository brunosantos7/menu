package br.com.menu.menudigital.product;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public boolean softDeleteProduct(Product product) {
		product.setDeleted(true);
		productRepository.save(product);
		
		return true;
	}

}
