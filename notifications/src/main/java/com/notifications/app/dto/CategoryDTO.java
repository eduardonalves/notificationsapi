package com.notifications.app.dto;

import java.io.Serializable;

import com.notifications.app.models.Category;

import jakarta.validation.constraints.NotEmpty;

public class CategoryDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	


	private Long id;
	
	@NotEmpty(message="Required field.")
	private String name;
	
	public CategoryDTO(Category category) {
		
		this.id = category.getId();
		this.name = category.getName();
	}
	
	
	public CategoryDTO() {
	
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
