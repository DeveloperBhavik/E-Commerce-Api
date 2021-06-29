package com.e_commerce_app.service;

import com.e_commerce_app.request.OrderDetailRequest;
import com.e_commerce_app.response.OrderDetailResponse;
import com.e_commerce_app.response.ResponseData;

public interface OrderDetailService {

	Object saveOrderDetail(OrderDetailRequest orderDetailRequest);

	ResponseData<OrderDetailResponse> getOrderDetailById(Long id);

	ResponseData<OrderDetailResponse> deleteOrderDetailById(Long id);
}