package br.com.menu.menudigital.product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private ProductRepository productRepository;
	
	public ProductController(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	@GetMapping("/{id}")
	public @ResponseBody Optional<Product> save(@PathVariable Long id) {
		return productRepository.findById(id);
	}

	@PostMapping
	public @ResponseBody Product save(@Valid ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {
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
	
	@PutMapping("/{id}")
	public @ResponseBody Product update (@PathVariable Long id, @Valid ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) {
		Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe produto com este id."));

		Product entityToSave = productDTO.toEntity();
		entityToSave.setImagePath(product.getImagePath());
		entityToSave.setId(product.getId());
		
		return productRepository.save(entityToSave);
	}
	
	@PutMapping("/{id}/image")
	public @ResponseBody Product updateProductImage (@PathVariable Long id, ProductDTO productDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {
		Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe produto com este id."));
		product.setCategoryId(productDTO.getCategoryId());
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		
		Path path = Paths.get(String.format("images/product/%s", product.getId())); 
		
		if(file != null) {
			
			if(path.toFile().exists()) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        try {
				Files.createDirectories(path);
			
	        
		        String filename = StringUtils.cleanPath(file.getOriginalFilename());
		        
		        Files.copy(file.getInputStream(), path.resolve(filename));
		        product.setImagePath(path.resolve(filename).toString());
		        
	        } catch (IOException e) {
				throw new IOException("Aconteceu algum problema ao salvar a imagem no disco.", e);
			}
	        
		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        product.setImagePath(null);
		}

		return productRepository.save(product);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteProduct(@PathVariable Long id)  {
		Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe produto com este id."));

		Path path = Paths.get(String.format("images/product/%s", product.getId())); 
        FileSystemUtils.deleteRecursively(path.toFile());
        
		productRepository.delete(product);
	}


}
