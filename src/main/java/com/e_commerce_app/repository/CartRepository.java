package com.e_commerce_app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

	List<Cart> findByUserEmail(String email);

	List<Cart> findByProductName(String name);
}