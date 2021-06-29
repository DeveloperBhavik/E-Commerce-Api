package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.entity.User;
import com.e_commerce_app.enums.OrderStatus;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.repository.OrderRepository;
import com.e_commerce_app.repository.UserRepository;
import com.e_commerce_app.request.OrderRequest;
import com.e_commerce_app.response.OrderResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.OrderService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Save order
	 */
	@Override
	public Object saveOrder(OrderRequest orderRequest) {
		
		Optional<User> userOptional = userRepository.findById(orderRequest.getUserId());
		if(!userOptional.isPresent())
			return new ResponseData<UserResponse>(MessageConstants.USER_NOT_FOUND, null, 420);
		String message = null;
		message = (orderRequest.getId() != null ? MessageConstants.ORDER_UPDATE_SUCCESS : MessageConstants.ORDER_SAVE_SUCCESS);
		Order order = new Order();
		order.setId(orderRequest.getId() != null ? orderRequest.getId() : null);
		order.setAmount(orderRequest.getAmount());
		order.setDate(orderRequest.getDate());
		order.setStatus(orderRequest.getStatus() != null ? orderRequest.getStatus() : OrderStatus.PLACED.toString());
		order.setUser(userOptional.get());
		orderRepository.save(order);
		return new ResponseData<OrderResponse>(message, setData(order), 200);
	}
	
	/**
	 * Set data in response
	 * @param order
	 * @return
	 */
	private OrderResponse setData(Order order) {
		
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setId(order.getId());
		orderResponse.setAmount(order.getAmount());
		orderResponse.setDate(order.getDate());
		orderResponse.setStatus(order.getStatus());
		orderResponse.setUserName(order.getUser().getFirstname());
		return orderResponse;
	}
	
	/**
	 * Get order by id
	 */
	@Override
	public ResponseData<OrderResponse> getOrderById(Long id) {
		
		Optional<Order> orderOptional = orderRepository.findById(id);
		ResponseData<OrderResponse> responseData  = orderOptional.isPresent() ? new ResponseData<OrderResponse>(MessageConstants.ORDER_FETCH_SUCCESS, setData(orderOptional.get()), 200) : new ResponseData<OrderResponse>(MessageConstants.ORDER_NOT_FOUND, null, 420);
		return responseData;
	}
	
	/**
	 * Delete order by id
	 */
	@Override
	public ResponseData<OrderResponse> deleteOrderById(Long id) {
		
		Optional<Order> orderOptional = orderRepository.findById(id);
		ResponseData<OrderResponse> responseData  = orderOptional.isPresent() ? new ResponseData<OrderResponse>(MessageConstants.ORDER_DELETE_SUCCESS, setData(orderOptional.get()), 200) : new ResponseData<OrderResponse>(MessageConstants.ORDER_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { 
	    	List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderOptional.get().getId());
	    	if(!CollectionUtils.isEmpty(orderDetails)) {
	    		for (OrderDetail orderDetail : orderDetails) {
					orderDetailRepository.deleteById(orderDetail.getId());
				}
	    	}
	    	orderRepository.deleteById(orderOptional.get().getId());
	    }
		return responseData;
	}
	
	/**
	 * Get list of all order
	 */
	@Override
	public Object getListOfAllOrder() {
		
		List<Order> orders = (List<Order>) orderRepository.findAll();
		List<ResponseData<OrderResponse>> responseDatas = new ArrayList<ResponseData<OrderResponse>>();
		orders.stream().forEach(elem -> responseDatas.add(new ResponseData<OrderResponse>(MessageConstants.ORDER_LIST_SUCCESS, setData(elem), 200)));
		return responseDatas;
	}
}