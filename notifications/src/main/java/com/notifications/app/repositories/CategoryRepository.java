package com.notifications.app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notifications.app.models.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findOneByName(String name);

}
