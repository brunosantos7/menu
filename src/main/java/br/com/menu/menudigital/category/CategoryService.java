package br.com.menu.menudigital.category;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

	boolean softDeleteCategory(Category category);

	Category saveCategory(@Valid CategoryDTO categoryDTO, MultipartFile file) throws IOException;

	Category updateCategoryImage(MultipartFile file, Category category) throws IOException;

}
