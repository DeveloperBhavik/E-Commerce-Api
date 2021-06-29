package com.e_commerce_app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.OrderDetail;

@Repository
public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {

	List<OrderDetail> findByProductName(String name);

	OrderDetail findByOrderUserEmail(String email);

	List<OrderDetail> findByOrderId(Long id);
}