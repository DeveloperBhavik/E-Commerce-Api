package com.e_commerce_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.entity.Product;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.repository.OrderRepository;
import com.e_commerce_app.repository.ProductRepository;
import com.e_commerce_app.request.OrderDetailRequest;
import com.e_commerce_app.response.OrderDetailResponse;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.OrderDetailService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * Save order detail
	 */
	@Override
	public Object saveOrderDetail(OrderDetailRequest orderDetailRequest) {
		
		Optional<Product> productOptional = productRepository.findById(orderDetailRequest.getProductId());
		if(!productOptional.isPresent())
			return new ResponseData<ProductResponse>(MessageConstants.PRODUCT_NOT_FOUND, null, 420);
		Optional<Order> orderOptional = orderRepository.findById(orderDetailRequest.getOrderId());
		if(!orderOptional.isPresent())
			return new ResponseData<ProductResponse>(MessageConstants.ORDER_NOT_FOUND, null, 420);
		String message = null;
		message = (orderDetailRequest.getId() != null ? MessageConstants.ORDER_DETAIL_UPDATE_SUCCESS : MessageConstants.ORDER_DETAIL_SAVE_SUCCESS);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(orderDetailRequest.getId() != null ? orderDetailRequest.getId() : null);
		orderDetail.setOrder(orderOptional.get());
		orderDetail.setPrice(orderDetailRequest.getPrice());
		orderDetail.setProduct(productOptional.get());
		orderDetail.setQuantity(orderDetailRequest.getQuantity());
		orderDetailRepository.save(orderDetail);
		return new ResponseData<OrderDetailResponse>(message, setData(orderDetail), 200);
	}
	
	/**
	 * Set data to response
	 * @param orderDetail
	 * @return
	 */
	private OrderDetailResponse setData(OrderDetail orderDetail) {
		
		OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
		orderDetailResponse.setId(orderDetail.getId());
		orderDetailResponse.setOrderDate(orderDetail.getOrder().getDate());
		orderDetailResponse.setOrderStatus(orderDetail.getOrder().getStatus());
		orderDetailResponse.setPrice(orderDetail.getPrice());
		orderDetailResponse.setProductName(orderDetail.getProduct().getName());
		orderDetailResponse.setQuantity(orderDetail.getQuantity());
		return orderDetailResponse;
	}
	
	/**
	 * Get order detail by id
	 */
	@Override
	public ResponseData<OrderDetailResponse> getOrderDetailById(Long id) {
		
		Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
		ResponseData<OrderDetailResponse> responseData  = orderDetailOptional.isPresent() ? new ResponseData<OrderDetailResponse>(MessageConstants.ORDER_DETAIL_FETCH_SUCCESS, setData(orderDetailOptional.get()), 200) : new ResponseData<OrderDetailResponse>(MessageConstants.ORDER_DETAIL_NOT_FOUND, null, 420);
		return responseData;
	}

	/**
	 * Delete order detail by id
	 */
	@Override
	public ResponseData<OrderDetailResponse> deleteOrderDetailById(Long id) {
		
		Optional<OrderDetail> orderDetailOptional = orderDetailRepository.findById(id);
		ResponseData<OrderDetailResponse> responseData  = orderDetailOptional.isPresent() ? new ResponseData<OrderDetailResponse>(MessageConstants.ORDER_DETAIL_DELETE_SUCCESS, setData(orderDetailOptional.get()), 200) : new ResponseData<OrderDetailResponse>(MessageConstants.ORDER_DETAIL_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { orderDetailRepository.deleteById(id); }
		return responseData;
	}
}