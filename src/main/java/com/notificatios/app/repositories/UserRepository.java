package com.notificatios.app.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notificatios.app.models.Category;
import com.notificatios.app.models.User;



@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findOneByName(String name);
	List<User>  findAllBySubscribed(Category category);
}
