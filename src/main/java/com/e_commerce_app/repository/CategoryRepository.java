package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

	Category findByName(String name);
}