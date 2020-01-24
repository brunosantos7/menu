package br.com.menu.menudigital.category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
import javassist.NotFoundException;


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
	public @ResponseBody Category saveCategory(CategoryDTO categoryDTO, @RequestParam(name="file", required=false) MultipartFile file) throws IOException {
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
	public @ResponseBody Category updateCategory (@PathVariable Long id, CategoryDTO categoryDTO, @RequestParam(name="file", required=false) MultipartFile file) throws NotFoundException, IOException {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist category with this id."));
		category.setMenuId(categoryDTO.getMenuId());
		category.setName(categoryDTO.getName());
		
		Path path = Paths.get(String.format("images/category/%s", category.getId())); 
		
		if(file != null) {
			
			if(path.toFile().exists()) {
		        FileSystemUtils.deleteRecursively(path.toFile());
			}
			
	        Files.createDirectories(path);
	        
	        String filename = StringUtils.cleanPath(file.getOriginalFilename());
	        
	        Files.copy(file.getInputStream(), path.resolve(filename));
	        category.setImagePath(path.resolve(filename).toString());
	        
		} else {
	        FileSystemUtils.deleteRecursively(path.toFile());
	        category.setImagePath(null);
		}

		return categoryRepository.save(category);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void deleteCategory(@PathVariable Long id) throws NotFoundException {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Does not exist category with this id."));
		Path path = Paths.get(String.format("images/category/%s", category.getId()));
		FileSystemUtils.deleteRecursively(path.toFile());
		
		categoryRepository.delete(category);
	}
}
