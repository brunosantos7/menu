package br.com.menu.menudigital.product;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

	boolean softDeleteProduct(Product product);

	Product saveProduct(@Valid ProductDTO productDTO, MultipartFile file) throws IOException;

}
