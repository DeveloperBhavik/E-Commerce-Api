package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

	OrderDetail findByProductName(String name);

	OrderDetail findByOrderUserEmail(String email);
}