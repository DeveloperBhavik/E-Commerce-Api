package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Cart;
import com.e_commerce_app.entity.OrderDetail;
import com.e_commerce_app.entity.Product;
import com.e_commerce_app.repository.CartRepository;
import com.e_commerce_app.repository.OrderDetailRepository;
import com.e_commerce_app.repository.ProductRepository;
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
	
	/**
	 * Save product
	 */
	@Override
	public ResponseData<ProductResponse> saveProduct(ProductRequest productRequest) {
		
		String message = null;
		message = (productRequest.getId() != null ? MessageConstants.PRODUCT_UPDATE_SUCCESS : MessageConstants.PRODUCT_SAVE_SUCCESS);
		Product product = new Product();
		product.setId(productRequest.getId() != null ? productRequest.getId() : null);
		product.setActive(1);
		product.setCategory(productRequest.getCategory());
		product.setDescription(productRequest.getDescription());
		product.setName(productRequest.getName());
		product.setPrice(productRequest.getPrice());
		product.setQuantity(productRequest.getQuantity());
		product.setSubCategory(productRequest.getSubCategory());
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
		productResponse.setCategory(product.getCategory());
		productResponse.setDescription(product.getDescription());
		productResponse.setName(product.getName());
		productResponse.setPrice(product.getPrice());
		productResponse.setQuantity(product.getQuantity());
		productResponse.setSubCategory(product.getSubCategory());
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
	    	Optional<Cart> cartOptional = Optional.ofNullable(cartRepository.findByProductName(productOptional.get().getName()));
	    	if(cartOptional.isPresent()) { cartRepository.deleteById(cartOptional.get().getId()); }
	    	Optional<OrderDetail> orderDetailOptional = Optional.ofNullable(orderDetailRepository.findByProductName(productOptional.get().getName()));
	    	if(orderDetailOptional.isPresent()) { orderDetailRepository.deleteById(orderDetailOptional.get().getId()); }
	    	Product product = productOptional.get(); product.setActive(0); productRepository.save(product); 
	    }
		return responseData;
	}
	
	/**
	 * Get list of all product
	 */
	@Override
	public Object getListOfAllProduct() {
		
		List<Product> products = ((List<Product>) productRepository.findAll()).stream().filter(p->p.getActive() == 1).collect(Collectors.toList());
		List<ResponseData<Product>> responseDatas = new ArrayList<ResponseData<Product>>();
		products.stream().forEach(elem -> responseDatas.add(setDataInResponse(elem)));
		return responseDatas;
	}
	
	/**
	 * Set data in response
	 * @param product
	 * @return
	 */
	private ResponseData<Product> setDataInResponse(Product product) {
		return new ResponseData<Product>(MessageConstants.USER_LIST_SUCCESS, product, 200);
	}
}