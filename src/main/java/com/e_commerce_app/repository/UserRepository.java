package com.e_commerce_app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce_app.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByEmail(String username);
}