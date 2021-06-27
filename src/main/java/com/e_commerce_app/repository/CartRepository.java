package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

	Cart findByUserEmail(String email);

	Cart findByProductName(String name);
}