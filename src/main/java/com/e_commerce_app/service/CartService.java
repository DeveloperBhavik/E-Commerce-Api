package com.e_commerce_app.service;

import com.e_commerce_app.request.CartRequest;
import com.e_commerce_app.response.CartResponse;
import com.e_commerce_app.response.ResponseData;

public interface CartService {

	Object saveCart(CartRequest cartRequest);

	ResponseData<CartResponse> getCartById(Long id);

	ResponseData<CartResponse> deleteCartById(Long id);
}