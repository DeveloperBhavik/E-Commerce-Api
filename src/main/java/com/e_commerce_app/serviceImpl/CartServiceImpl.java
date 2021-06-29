package com.e_commerce_app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Cart;
import com.e_commerce_app.entity.Product;
import com.e_commerce_app.entity.User;
import com.e_commerce_app.repository.CartRepository;
import com.e_commerce_app.repository.ProductRepository;
import com.e_commerce_app.repository.UserRepository;
import com.e_commerce_app.request.CartRequest;
import com.e_commerce_app.response.CartResponse;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.CartService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Save cart
	 */
	@Override
	public Object saveCart(CartRequest cartRequest) {
		
		Optional<Product> productOptional = productRepository.findById(cartRequest.getProductId());
		if(!productOptional.isPresent())
			return new ResponseData<ProductResponse>(MessageConstants.PRODUCT_NOT_FOUND, null, 420);
		Optional<User> userOptional = userRepository.findById(cartRequest.getUserId());
		if(!userOptional.isPresent())
			return new ResponseData<UserResponse>(MessageConstants.USER_NOT_FOUND, null, 420);
		String message = null;
		message = (cartRequest.getId() != null ? MessageConstants.CART_UPDATE_SUCCESS : MessageConstants.CART_SAVE_SUCCESS);
		Cart cart = new Cart();
		cart.setId(cartRequest.getId() != null ? cartRequest.getId() : null);
		cart.setProduct(productOptional.get());
		cart.setQuantity(cartRequest.getQuantity());
		cart.setUser(userOptional.get());
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
		cartResponse.setProductName(cart.getProduct().getName());
		cartResponse.setQuantity(cart.getQuantity());
		cartResponse.setUserName(cart.getUser().getFirstname());
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