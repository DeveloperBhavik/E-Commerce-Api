package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.e_commerce_app.entity.Cart;
import com.e_commerce_app.entity.Category;
import com.e_commerce_app.entity.Order;
import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.entity.Product;
import com.e_commerce_app.entity.SubCategory;
import com.e_commerce_app.repository.CartRepository;
import com.e_commerce_app.repository.CategoryRepository;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.repository.OrderRepository;
import com.e_commerce_app.repository.ProductRepository;
import com.e_commerce_app.repository.SubCategoryRepository;
import com.e_commerce_app.request.ProductRequest;
import com.e_commerce_app.response.ProductResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.ProductService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * Save product
	 */
	@Override
	public Object saveProduct(ProductRequest productRequest) {
		
		Optional<Category> categoryOptional = categoryRepository.findById(productRequest.getCategoryId());
		if(!categoryOptional.isPresent())
			return new ResponseData<ProductResponse>(MessageConstants.CATEGORY_NOT_FOUND, null, 420);
		Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(productRequest.getSubCategoryId());
		if(!subCategoryOptional.isPresent())
			return new ResponseData<ProductResponse>(MessageConstants.SUB_CATEGORY_NOT_FOUND, null, 420);
		String message = null;
		message = (productRequest.getId() != null ? MessageConstants.PRODUCT_UPDATE_SUCCESS : MessageConstants.PRODUCT_SAVE_SUCCESS);
		Product product = new Product();
		product.setId(productRequest.getId() != null ? productRequest.getId() : null);
		product.setActive(1);
		product.setCategory(categoryOptional.get());
		product.setDescription(productRequest.getDescription());
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setSubCategory(subCategoryOptional.get());
		product.setWarrantyInfo(productRequest.getWarrantyInfo());
		productRepository.save(product);
		return new ResponseData<ProductResponse>(message, setData(product), 200);
	}
	
	/**
	 * Set data in response
	 * @param product
	 * @return
	 */
	private ProductResponse setData(Product product) {
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setCategoryName(product.getCategory().getName());
		productResponse.setDescription(product.getDescription());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setSubCategoryName(product.getSubCategory().getName());
		productResponse.setWarrantyInfo(product.getWarrantyInfo());
		return productResponse;
	}
	
	/**
	 * Check product name existence 
	 */
	@Override
	public Product checkProductExistence(String name) {
		return productRepository.findByName(name);
	}
	
	/**
	 * Get product by id
	 */
	@Override
	public ResponseData<ProductResponse> getProductById(Long id) {
		
		Optional<Product> productOptional = productRepository.findById(id);
		ResponseData<ProductResponse> responseData  = productOptional.isPresent() ? new ResponseData<ProductResponse>(MessageConstants.PRODUCT_FETCH_SUCCESS, setData(productOptional.get()), 200) : new ResponseData<ProductResponse>(MessageConstants.PRODUCT_NOT_FOUND, null, 420);
		return responseData;
	}
	
	/**
	 * Delete product by id
	 */
	@Override
	public ResponseData<ProductResponse> deleteProductById(Long id) {
		
		Optional<Product> productOptional = productRepository.findById(id);
		ResponseData<ProductResponse> responseData  = productOptional.isPresent() ? new ResponseData<ProductResponse>(MessageConstants.PRODUCT_DELETE_SUCCESS, setData(productOptional.get()), 200) : new ResponseData<ProductResponse>(MessageConstants.PRODUCT_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { 
	    	List<OrderDetail> orderDetails = orderDetailRepository.findByProductName(productOptional.get().getName());
			if (!CollectionUtils.isEmpty(orderDetails)) {
				for (OrderDetail orderDetail : orderDetails) {
					List<Order> orders = orderRepository.findByUserEmail(orderDetail.getOrder().getUser().getEmail());
					for (Order order : orders) {
						orderDetailRepository.deleteById(orderDetail.getId());
						orderRepository.deleteById(order.getId());
					}
				}
			}
			List<Cart> carts = cartRepository.findByProductName(productOptional.get().getName());
			if (!CollectionUtils.isEmpty(carts)) {
				for (Cart cart : carts) {
					cartRepository.deleteById(cart.getId());
				}
			}
			productRepository.deleteById(productOptional.get().getId());
	    }
		return responseData;
	}
	
	/**
	 * Get list of all product
	 */
	@Override
	public Object getListOfAllProduct() {
		
		List<Product> products = ((List<Product>) productRepository.findAll()).stream().filter(p->p.getActive() == 1).collect(Collectors.toList());
		List<ResponseData<ProductResponse>> responseDatas = new ArrayList<ResponseData<ProductResponse>>();
		products.stream().forEach(elem -> responseDatas.add(new ResponseData<ProductResponse>(MessageConstants.PRODUCT_LIST_SUCCESS, setData(elem), 200)));
		return responseDatas;
	}
}