package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.enums.OrderStatus;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.repository.OrderRepository;
import com.e_commerce_app.request.OrderRequest;
import com.e_commerce_app.response.OrderResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.OrderService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	/**
	 * Save order
	 */
	@Override
	public ResponseData<OrderResponse> saveOrder(OrderRequest orderRequest) {
		
		String message = null;
		message = (orderRequest.getId() != null ? MessageConstants.ORDER_UPDATE_SUCCESS : MessageConstants.ORDER_SAVE_SUCCESS);
		Order order = new Order();
		order.setId(orderRequest.getId() != null ? orderRequest.getId() : null);
		order.setAmount(orderRequest.getAmount());
		order.setDate(orderRequest.getDate());
		order.setStatus(orderRequest.getStatus() != null ? orderRequest.getStatus() : OrderStatus.PLACED.toString());
		order.setUser(orderRequest.getUser());
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
		orderResponse.setUser(order.getUser());
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
	    	Optional<OrderDetail> orderDetailOptional = Optional.ofNullable(orderDetailRepository.findByOrderUserEmail(orderOptional.get().getUser().getEmail()));
	    	if(orderDetailOptional.isPresent()) { orderDetailRepository.deleteById(orderDetailOptional.get().getId()); }
	    	orderRepository.deleteById(id); 
	    }
		return responseData;
	}
	
	/**
	 * Get list of all order
	 */
	@Override
	public Object getListOfAllOrder() {
		
		List<Order> orders = (List<Order>) orderRepository.findAll();
		List<ResponseData<Order>> responseDatas = new ArrayList<ResponseData<Order>>();
		orders.stream().forEach(elem -> responseDatas.add(setDataInResponse(elem)));
		return responseDatas;
	}
	
	/**
	 * Set data in response
	 * @param order
	 * @return
	 */
	private ResponseData<Order> setDataInResponse(Order order) {
		return new ResponseData<Order>(MessageConstants.ORDER_LIST_SUCCESS, order, 200);
	}
}