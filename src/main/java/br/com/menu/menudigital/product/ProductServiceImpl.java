package br.com.menu.menudigital.product;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.menu.menudigital.utils.file.FileUtils;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	
	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	public Product saveProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
		Product product = productRepository.save(productDTO.toEntity());
		
		if(file != null) {
			String path = FileUtils.saveOnDisk(file, product.getRestaurantId());
			product.setImagePath(path);
	        return productRepository.save(product);
		}
		return product;
	}

	@Override
	public boolean softDeleteProduct(Product product) {
		product.setDeleted(true);
		productRepository.save(product);
		
		return true;
	}
	
	@Override
	public Product updateProductImage(MultipartFile file, Product product) throws IOException {
		
		String newPath = null;
		
		if(product.getImagePath() != null) {
			FileUtils.deleteFromDisk(product.getImagePath());
		}
		
		if(file != null) {
			newPath = FileUtils.saveOnDisk(file, product.getId());
		}

		product.setImagePath(newPath);
		return productRepository.save(product);
		
	}

}
