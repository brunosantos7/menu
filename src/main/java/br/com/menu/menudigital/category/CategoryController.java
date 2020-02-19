package br.com.menu.menudigital.category;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
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
import br.com.menu.menudigital.user.User;
import br.com.menu.menudigital.user.UserRepository;
import br.com.menu.menudigital.user.UserService;
import br.com.menu.menudigital.utils.exception.PaymentRequiredException;


@Controller
@RequestMapping("/category")
public class CategoryController {
	
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	private CategoryService categoryService;
	private UserService userService;
	private UserRepository userRepository;
	
	public CategoryController(CategoryRepository categoryRepository, ProductRepository productRepository,
			CategoryService categoryService, UserService userService, UserRepository userRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.categoryService = categoryService;
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@GetMapping("/{id}/products")
	public @ResponseBody List<Product> getProductsByCategory(@PathVariable Long id){
		return this.productRepository.findByCategoryId(id);
	}

	@PostMapping
	public @ResponseBody Category save(@Valid CategoryDTO categoryDTO, @RequestParam(name="file", required=false) MultipartFile file, Principal principal) throws IOException, PaymentRequiredException {
		User user = userRepository.findByEmail(principal.getName());
		
		boolean isMax = userService.hasMaxCategoryForPlan(user);
		
		if(isMax) {
			throw new PaymentRequiredException("Atingiu o limite maximo de categorias para este plano.");
		}
		
		return categoryService.saveCategory(categoryDTO, file);
	}

	
	@PutMapping("/{id}")
	public @ResponseBody Category update (@PathVariable Long id, @Valid CategoryDTO categoryDTO) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
		Category entityToSave = categoryDTO.toEntity();
		entityToSave.setId(category.getId());
		entityToSave.setImagePath(category.getImagePath());
		
		return categoryRepository.save(entityToSave);
	}
	
	@PutMapping("/{id}/image")
	public @ResponseBody Category updateCategoryImage (@PathVariable Long id, @RequestParam(name="file", required=false) MultipartFile file) throws IOException  {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
		return categoryService.updateCategoryImage(file, category);
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody void delete(@PathVariable Long id)  {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe categoria com este id."));
		
//		Path path = Paths.get(String.format("images/category/%s", category.getId()));
//		FileSystemUtils.deleteRecursively(path.toFile());
		
		categoryService.softDeleteCategory(category);
	}
}
