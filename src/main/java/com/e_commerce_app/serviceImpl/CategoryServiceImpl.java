package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import com.e_commerce_app.request.CategoryRequest;
import com.e_commerce_app.response.CategoryResponse;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.service.CategoryService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CartRepository cartRepository;

	/**
	 * Save category
	 */
	@Override
	public ResponseData<CategoryResponse> saveCategory(CategoryRequest categoryRequest) {

		String message = null;
		message = (categoryRequest.getId() != null ? MessageConstants.CATEGORY_UPDATE_SUCCESS : MessageConstants.CATEGORY_SAVE_SUCCESS);
		Category category = new Category();
		category.setId(categoryRequest.getId() != null ? categoryRequest.getId() : null);
		category.setName(categoryRequest.getName());
		categoryRepository.save(category);
		return new ResponseData<CategoryResponse>(message, setData(category), 200);
	}

	/**
	 * Set data in response
	 * 
	 * @param category
	 * @return
	 */
	private CategoryResponse setData(Category category) {

		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(category.getId());
		categoryResponse.setName(category.getName());
		return categoryResponse;
	}

	/**
	 * Check category name existence
	 */
	@Override
	public Category checkCategoryExistence(String name) {
		return categoryRepository.findByName(name);
	}

	/**
	 * Get category by id
	 */
	@Override
	public ResponseData<CategoryResponse> getCategoryById(Long id) {

		Optional<Category> categoryOptional = categoryRepository.findById(id);
		ResponseData<CategoryResponse> responseData = categoryOptional.isPresent()? new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_FETCH_SUCCESS,setData(categoryRepository.findById(id).get()), 200) : new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_NOT_FOUND, null, 420);
		return responseData;
	}

	/**
	 * Delete category by id
	 */
	@Override
	public ResponseData<CategoryResponse> deleteCategoryById(Long id) {

		Optional<Category> categoryOptional = categoryRepository.findById(id);
		ResponseData<CategoryResponse> responseData = categoryOptional.isPresent() ? new ResponseData<CategoryResponse>(MessageConstants.USER_DELETE_SUCCESS, setData(categoryRepository.findById(id).get()), 200) : new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_NOT_FOUND, null, 420);
		if (responseData.getStatus() == 200) {
			List<SubCategory> subCategories = subCategoryRepository.findByCategoryName(categoryOptional.get().getName());
			if (!CollectionUtils.isEmpty(subCategories)) {
				for (SubCategory subCategory : subCategories) {
					List<Product> products = productRepository.findBySubCategoryName(subCategory.getName());
					if (!CollectionUtils.isEmpty(products)) {
						for (Product product : products) {
							List<OrderDetail> orderDetails = orderDetailRepository.findByProductName(product.getName());
							if (!CollectionUtils.isEmpty(orderDetails)) {
								for (OrderDetail orderDetail : orderDetails) {
									List<Order> orders = orderRepository.findByUserEmail(orderDetail.getOrder().getUser().getEmail());
									for (Order order : orders) {
										orderDetailRepository.deleteById(orderDetail.getId());
										orderRepository.deleteById(order.getId());
									}
								}
							}
							List<Cart> carts = cartRepository.findByProductName(product.getName());
							if (!CollectionUtils.isEmpty(carts)) {
								for (Cart cart : carts) {
									cartRepository.deleteById(cart.getId());
								}
							}
							productRepository.deleteById(product.getId());
						}
					}
					subCategoryRepository.deleteById(subCategory.getId());
				}
			}
			categoryRepository.deleteById(id);
		}
		return responseData;
	}

	/**
	 * Get list of all categories
	 */
	@Override
	public Object getListOfAllCategories() {

		List<Category> categories = (List<Category>) categoryRepository.findAll();
		List<ResponseData<CategoryResponse>> responseDatas = new ArrayList<ResponseData<CategoryResponse>>();
		categories.stream().forEach(elem -> responseDatas.add(new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_LIST_SUCCESS, setData(elem), 200)));
		return responseDatas;
	}
}