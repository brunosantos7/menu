package br.com.menu.menudigital.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javassist.NotFoundException;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@PostMapping
	public @ResponseBody Product saveCategory(ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {
		Product product = productRepository.save(productDTO.toEntity());
		
		if(file != null) {
			Path path = Paths.get(String.format("images/product/%s", product.getId())); 
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        product.setImagePath(path.resolve(filename).toString());
	        
	        return productRepository.save(product);
	        
		}
		return product;
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Product updateCategory (@PathVariable Long id, ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws Exception {
		Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist product with this id."));
		product.setCategoryId(productDTO.getCategoryId());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		
		Path path = Paths.get(String.format("images/PRODUCT/%s", product.getId())); 
		
		if(file != null) {
			
			if(path.toFile().exists()) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        product.setImagePath(path.resolve(filename).toString());
	        
		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        product.setImagePath(null);
		}

		return productRepository.save(product);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCategory(@PathVariable Long id) throws Exception {
		Product product = productRepository.findById(id).orElseThrow(() -> new Exception("Does not exist product with this id."));
		productRepository.delete(product);
	}


}
