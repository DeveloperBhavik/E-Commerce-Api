package com.e_commerce_app.service;

import com.e_commerce_app.request.OrderRequest;
import com.e_commerce_app.response.OrderResponse;
import com.e_commerce_app.response.ResponseData;

public interface OrderService {

	Object saveOrder(OrderRequest orderRequest);

	ResponseData<OrderResponse> getOrderById(Long id);

	ResponseData<OrderResponse> deleteOrderById(Long id);

	Object getListOfAllOrder();
}