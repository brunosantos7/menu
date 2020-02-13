package br.com.menu.menudigital.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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
			Path path = Paths.get(String.format("images/product/%s", product.getId())); 
			
	        try {
				Files.createDirectories(path);
	        
		        String filename = StringUtils.cleanPath(file.getOriginalFilename());
		        
		        Files.copy(file.getInputStream(), path.resolve(filename));
		        product.setImagePath(path.resolve(filename).toString());
	        } catch (IOException e) {
				throw new IOException("Aconteceu algum problema ao salvar a imagem no disco.", e);
			}
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

}
