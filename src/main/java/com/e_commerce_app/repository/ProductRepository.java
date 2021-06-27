package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

	Product findByName(String name);

	Product findByCategoryName(String name);

	Product findBySubCategoryName(String name);
}