package com.e_commerce_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Cart;
import com.e_commerce_app.repository.CartRepository;
import com.e_commerce_app.request.CartRequest;
import com.e_commerce_app.response.CartResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.CartService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;
	
	/**
	 * Save cart
	 */
	@Override
	public ResponseData<CartResponse> saveCart(CartRequest cartRequest) {
		
		String message = null;
		message = (cartRequest.getId() != null ? MessageConstants.CART_UPDATE_SUCCESS : MessageConstants.CART_SAVE_SUCCESS);
		Cart cart = new Cart();
		cart.setId(cartRequest.getId() != null ? cartRequest.getId() : null);
		cart.setProduct(cartRequest.getProduct());
		cart.setQuantity(cartRequest.getQuantity());
		cart.setUser(cartRequest.getUser());
		cartRepository.save(cart);
		return new ResponseData<CartResponse>(message, setData(cart), 200);
	}
	
	/**
	 * Set data in response
	 * @param cart
	 * @return
	 */
	private CartResponse setData(Cart cart) {
		
		CartResponse cartResponse = new CartResponse();
		cartResponse.setId(cart.getId());
		cartResponse.setProduct(cart.getProduct());
		cartResponse.setQuantity(cart.getQuantity());
		cartResponse.setUser(cart.getUser());
		return cartResponse;
	}
	
	/**
	 * Get cart by id
	 */
	@Override
	public ResponseData<CartResponse> getCartById(Long id) {
		
		Optional<Cart> cartOptional = cartRepository.findById(id);
		ResponseData<CartResponse> responseData  = cartOptional.isPresent() ? new ResponseData<CartResponse>(MessageConstants.CART_FETCH_SUCCESS, setData(cartOptional.get()), 200) : new ResponseData<CartResponse>(MessageConstants.CART_NOT_FOUND, null, 420);
		return responseData;
	}

	/**
	 * Delete cart by id
	 */
	@Override
	public ResponseData<CartResponse> deleteCartById(Long id) {
		
		Optional<Cart> cartOptional = cartRepository.findById(id);
		ResponseData<CartResponse> responseData  = cartOptional.isPresent() ? new ResponseData<CartResponse>(MessageConstants.CART_DELETE_SUCCESS, setData(cartOptional.get()), 200) : new ResponseData<CartResponse>(MessageConstants.CART_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { cartRepository.deleteById(id); }
		return responseData;
	}
}