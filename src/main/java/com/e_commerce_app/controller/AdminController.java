package com.e_commerce_app.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/")
@Api(value = "Admin", produces = "application/json")
public class AdminController {

}