package com.e_commerce_app.serviceImpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Cart;
import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.User;
import com.e_commerce_app.enums.Role;
import com.e_commerce_app.repository.CartRepository;
import com.e_commerce_app.repository.OrderRepository;
import com.e_commerce_app.repository.UserRepository;
import com.e_commerce_app.request.UserRequest;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.UserResponse;
import com.e_commerce_app.service.UserService;
import com.e_commerce_app.utils.MessageConstants;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Save user
	 */
	@Override
	public ResponseData<UserResponse> saveUser(UserRequest userRequest) {
		
		String message = null;
		message = (userRequest.getId() != null ? MessageConstants.USER_UPDATE_SUCCESS : MessageConstants.USER_SAVE_SUCCESS);
		User user = new User();
		user.setId(userRequest.getId() != null ? userRequest.getId() : null);
		user.setActive(1);
		user.setEmail(userRequest.getEmail());
		user.setFirstname(userRequest.getFirstname());
		user.setRole(Role.ROLE_USER.toString());
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		userRepository.save(user);
		return new ResponseData<UserResponse>(message, setData(user), 200);
	}
	
	/**
	 * Find user by email
	 */
	@Override
	public User findUserByEmail(String email) {
		
		User user = userRepository.findByEmail(email);
		return user = (user == null) ? null : user;
	}
	
	/**
	 * Find by user
	 */
	@Override
	public User findLoginUser() {
		
		Principal principal = request.getUserPrincipal();
		return principal == null ? null : userRepository.findByEmail(principal.getName());
	}

	/**
	 * Check is user login
	 */
	@Override
	public Object checkLogin() {
		
		User user = findLoginUser();
		return (user == null ? new ResponseData<User>(MessageConstants.PLEASE_TRY_AGAIN, null, 420) : new ResponseData<UserResponse>(MessageConstants.LOGIN_SUCCESS, setData(user), 200));
	}
	
	/**
	 * Set data in response
	 * @param user
	 * @return
	 */
	private UserResponse setData(User user) {
		
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setFirstname(user.getFirstname());
		userResponse.setEmail(user.getEmail());
		return userResponse;
	}

	/**
	 * Return list of user role
	 */
	@Override
	public Collection<? extends GrantedAuthority> getUserRoleList(String username) {
		
		User user = userRepository.findByEmail(username);
		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
		grantedAuths.add(new SimpleGrantedAuthority(user.getRole()));
	    return grantedAuths;
	}

	/**
	 * Get user by id
	 */
	@Override
	public ResponseData<UserResponse> getUserById(Long id) {
		
		Optional<User> userOptional = userRepository.findById(id);
		ResponseData<UserResponse> responseData  = userOptional.isPresent() ? new ResponseData<UserResponse>(MessageConstants.USER_FETCH_SUCCESS, setData(userRepository.findById(id).get()), 200) : new ResponseData<UserResponse>(MessageConstants.USER_NOT_FOUND, null, 420);
		return responseData;
	}

	/**
	 * Delete user by id
	 */
	@Override
	public ResponseData<UserResponse> deleteUserById(Long id) {
		
		Optional<User> userOptional = userRepository.findById(id);
		ResponseData<UserResponse> responseData  = userOptional.isPresent() ? new ResponseData<UserResponse>(MessageConstants.USER_DELETE_SUCCESS, setData(userRepository.findById(id).get()), 200) : new ResponseData<UserResponse>(MessageConstants.USER_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { 
	    	Optional<Cart> cartOptional = Optional.ofNullable(cartRepository.findByUserEmail(userOptional.get().getEmail()));
	    	if(cartOptional.isPresent()) { cartRepository.deleteById(cartOptional.get().getId()); }
	    	Optional<Order> orderOptional = Optional.ofNullable(orderRepository.findByUserEmail(userOptional.get().getEmail()));
	    	if(orderOptional.isPresent()) { orderRepository.deleteById(orderOptional.get().getId()); }
	    	User user = userOptional.get(); user.setActive(0); userRepository.save(user); 
	    }
		return responseData;
	}
	
	/**
	 * Check email existence
	 */
	@Override
	public User checkEmailAddressExistance(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * Get list of all user
	 */
	@Override
	public Object getListOfAllUser() {
		
		List<User> users = ((List<User>) userRepository.findAll()).stream().filter(u->u.getActive() == 1).collect(Collectors.toList());
		List<ResponseData<UserResponse>> responseDatas = new ArrayList<ResponseData<UserResponse>>();
		users.stream().forEach(elem -> responseDatas.add(new ResponseData<UserResponse>(MessageConstants.USER_LIST_SUCCESS, setData(elem), 200)));
		return responseDatas;
	}
}