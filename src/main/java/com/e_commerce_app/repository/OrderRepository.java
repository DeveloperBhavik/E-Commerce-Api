package com.e_commerce_app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	List<Order> findByUserEmail(String email);
}