package com.notificatios.app.services;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificatios.app.exceptions.ObjectNotFoundException;
import com.notificatios.app.models.Category;

import com.notificatios.app.repositories.CategoryRepository;

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
	
	public Category findById(int id) throws ObjectNotFoundException {
		Optional<Category> category =categoryRepository.findById(id);
		return category.orElseThrow( () -> new ObjectNotFoundException("Category not found: "+ id +" Tipo: " + Category.class.getName()));
	}

}