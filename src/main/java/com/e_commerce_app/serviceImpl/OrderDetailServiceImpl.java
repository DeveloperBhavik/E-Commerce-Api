package com.e_commerce_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.request.OrderDetailRequest;
import com.e_commerce_app.response.OrderDetailResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.OrderDetailService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	/**
	 * Save order detail
	 */
	@Override
	public ResponseData<OrderDetailResponse> saveOrderDetail(OrderDetailRequest orderDetailRequest) {
		
		String message = null;
		message = (orderDetailRequest.getId() != null ? MessageConstants.ORDER_DETAIL_UPDATE_SUCCESS : MessageConstants.ORDER_DETAIL_SAVE_SUCCESS);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setId(orderDetailRequest.getId() != null ? orderDetailRequest.getId() : null);
		orderDetail.setOrder(orderDetailRequest.getOrder());
		orderDetail.setPrice(orderDetailRequest.getPrice());
		orderDetail.setProduct(orderDetailRequest.getProduct());
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
		orderDetailResponse.setOrder(orderDetail.getOrder());
		orderDetailResponse.setPrice(orderDetail.getPrice());
		orderDetailResponse.setProduct(orderDetail.getProduct());
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