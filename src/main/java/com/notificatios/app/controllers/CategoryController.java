package com.notificatios.app.controllers;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.notificatios.app.dto.CategoryDTO;
import com.notificatios.app.exceptions.ObjectNotFoundException;
import com.notificatios.app.models.Category;
import com.notificatios.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/list")
	public ResponseEntity<List<CategoryDTO>> list() {
		List<Category> categories = categoryService.findAll();
		 List<CategoryDTO> categoryDTO = new ArrayList<>();
		 categories.stream().forEach(category-> categoryDTO.add(new CategoryDTO(category)));
		 return ResponseEntity.ok().body(categoryDTO);
	}
	
	@GetMapping("/view/{id}")
	public ResponseEntity<CategoryDTO> view(@PathVariable(value = "id") Long id) throws ObjectNotFoundException {
		Category category = categoryService.findById(id);
		 return ResponseEntity.ok().body(new CategoryDTO(category));
	}
	
	 @PostMapping("/add")
	 public ResponseEntity<Category> add(@Valid @RequestBody CategoryDTO categoryDTO) {
		 Category category = categoryService.fromDTO(categoryDTO);
		 category=categoryService.createIfNotExist(category);
		 return new ResponseEntity<>(category, HttpStatus.CREATED);
	 }
	 
	@PutMapping("/edit/{id}")
	public ResponseEntity<Void> update(@PathVariable(value = "id") Long id, @Valid @RequestBody CategoryDTO categoryDTO) throws ObjectNotFoundException{
		Category category = categoryService.fromDTO(categoryDTO);
		categoryService.updateCategory(id, category);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/delete/{id}")
	public Map<String, Boolean>  delete(@PathVariable(value="id") Long id)throws ObjectNotFoundException{
		categoryService.deleteCategory(id);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
