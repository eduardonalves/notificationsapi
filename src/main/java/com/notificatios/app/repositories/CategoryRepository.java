package com.notificatios.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificatios.app.models.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Category findOneByName(String name);

}
