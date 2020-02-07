package br.com.menu.menudigital.image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.menu.menudigital.category.Category;
import br.com.menu.menudigital.category.CategoryRepository;
import br.com.menu.menudigital.product.Product;
import br.com.menu.menudigital.product.ProductRepository;
import br.com.menu.menudigital.restaurant.Restaurant;
import br.com.menu.menudigital.restaurant.RestaurantRepository;

@Controller
@RequestMapping("/images")
public class ImageController {
	
	private CategoryRepository categoryRepository;

	private RestaurantRepository restaurantRepository;

	private ProductRepository productRepository;
	
	public ImageController(CategoryRepository categoryRepository, RestaurantRepository restaurantRepository,
			ProductRepository productRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.restaurantRepository = restaurantRepository;
		this.productRepository = productRepository;
	}

	@GetMapping("/category/{id}")
	public void getCategoryImage(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException, IOException {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe imagem com este id."));
		
		FileSystemResource fileSystemResource = new FileSystemResource(category.getImagePath());
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(new FileInputStream(fileSystemResource.getFile()), response.getOutputStream());
	}
	
	@GetMapping("/restaurant/{id}")
	public void getRestaurantImage(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException, IOException {
		Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe imagem com este id."));
		
		FileSystemResource fileSystemResource = new FileSystemResource(restaurant.getImagePath());
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(new FileInputStream(fileSystemResource.getFile()), response.getOutputStream());
	}
	
	@GetMapping("/product/{id}")
	public void getProductImage(@PathVariable Long id, HttpServletResponse response) throws FileNotFoundException, IOException {
		Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nao existe imagem com este id."));
		
		FileSystemResource fileSystemResource = new FileSystemResource(product.getImagePath());
	    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
	    IOUtils.copy(new FileInputStream(fileSystemResource.getFile()), response.getOutputStream());
	}


}
