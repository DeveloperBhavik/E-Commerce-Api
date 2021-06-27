package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.SubCategory;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {

	SubCategory findByName(String name);

	SubCategory findByCategoryName(String name);
}