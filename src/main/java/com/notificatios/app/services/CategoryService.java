package com.notificatios.app.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificatios.app.dto.CategoryDTO;
import com.notificatios.app.exceptions.ObjectNotFoundException;
import com.notificatios.app.models.Category;
import com.notificatios.app.repositories.CategoryRepository;

import jakarta.validation.Valid;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
		
	public Category create(Category category) {
		return categoryRepository.save(category);
	}
	public Category createIfNotExist(Category category) {
		Category exitingCategory = categoryRepository.findOneByName(category.getName());
		if(exitingCategory == null) {
			return categoryRepository.save(category);
		}else {
			return exitingCategory;
		}
	}
	
	public Category findById(Long id) throws ObjectNotFoundException {
		Optional<Category> category =categoryRepository.findById(Math.toIntExact(id));
		return category.orElseThrow( () -> new ObjectNotFoundException("Category not found: "+ id +" Category: " + Category.class.getName()));
	}
	
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
	public Category fromDTO(@Valid CategoryDTO categoryDTO) {
		Category category = new Category(categoryDTO.getId(),categoryDTO.getName());
		return category;
	}
	
	public void deleteCategory(Long id) {
		Optional<Category> optionalCategory = categoryRepository.findById(Math.toIntExact(id));
		Category category =optionalCategory.orElseThrow( () -> new ObjectNotFoundException("Category not found: "+ id +" Category: " + Category.class.getName()));
		categoryRepository.delete(category);
	}
	
	public void updateCategory(Long id, Category updatedCategory) {
		Optional<Category> optionalCategory = categoryRepository.findById(Math.toIntExact(id));
		Category category =optionalCategory.orElseThrow( () -> new ObjectNotFoundException("Category not found: "+ id +" Category: " + Category.class.getName()));
		category.setName(updatedCategory.getName());
		categoryRepository.save(category);
		
	}

}