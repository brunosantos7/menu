package br.com.menu.menudigital.category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

import br.com.menu.menudigital.product.Product;
import br.com.menu.menudigital.product.ProductRepository;


@Controller
@RequestMapping("/category")
public class CategoryController {
	
	private CategoryRepository categoryRepository;
	
	private ProductRepository productRepository;
	
	public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}
	
	@GetMapping("/{id}/products")
	public @ResponseBody List<Product> getProductsByCategory(@PathVariable Long id){
		return this.productRepository.findByCategoryId(id);
	}

	@PostMapping
	public @ResponseBody Category save(@Valid CategoryDTO categoryDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {
		Category category = categoryRepository.save(categoryDTO.toEntity());
		
		if(file != null) {
			Path path = Paths.get(String.format("images/category/%s", category.getId())); 
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        category.setImagePath(path.resolve(filename).toString());
	        
	        return categoryRepository.save(category);
	        
		}
		return category;
	}
	
	@PutMapping("/{id}")
	public @ResponseBody Category update (@PathVariable Long id, @Valid CategoryDTO categoryDTO, @RequestParam(name="file", required=false) MultipartFile file) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
		Category entityToSave = categoryDTO.toEntity();
		entityToSave.setId(category.getId());
		entityToSave.setImagePath(category.getImagePath());
		
		return categoryRepository.save(entityToSave);
	}
	
	@PutMapping("/{id}/image")
	public @ResponseBody Category updateCategoryImage (@PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) throws IOException  {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
		Path path = Paths.get(String.format("images/category/%s", category.getId())); 
		
		if(file != null) {
			
			if(path.toFile().exists()) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        try {
				Files.createDirectories(path);
			
		        String filename = StringUtils.cleanPath(file.getOriginalFilename());
		        
		        Files.copy(file.getInputStream(), path.resolve(filename));
		        category.setImagePath(path.resolve(filename).toString());
	        } catch (IOException e) {
				throw new IOException("Aconteceu algo de errado ao atualizar a imagem", e);
			}
	        
	        
		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        category.setImagePath(null);
		}
		
		return categoryRepository.save(category);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void delete(@PathVariable Long id)  {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
		Path path = Paths.get(String.format("images/category/%s", category.getId()));
		FileSystemUtils.deleteRecursively(path.toFile());
		
		categoryRepository.delete(category);
	}
}
