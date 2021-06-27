package com.e_commerce_app.service;

import com.e_commerce_app.entity.Product;
import com.e_commerce_app.request.ProductRequest;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;

public interface ProductService {

	ResponseData<ProductResponse> saveProduct(ProductRequest productRequest);

	Product checkProductExistence(String name);

	ResponseData<ProductResponse> getProductById(Long id);

	ResponseData<ProductResponse> deleteProductById(Long id);

	Object getListOfAllProduct();
}