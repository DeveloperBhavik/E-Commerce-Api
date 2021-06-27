package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.e_commerce_app.entity.Category;
import com.e_commerce_app.entity.Product;
import com.e_commerce_app.entity.SubCategory;
import com.e_commerce_app.repository.CategoryRepository;
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
		ResponseData<CategoryResponse> responseData  = categoryOptional.isPresent() ? new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_FETCH_SUCCESS, setData(categoryRepository.findById(id).get()), 200) : new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_NOT_FOUND, null, 420);
		return responseData;
	}
	
	/**
	 * Delete category by id
	 */
	@Override
	public ResponseData<CategoryResponse> deleteCategoryById(Long id) {
		
		Optional<Category> categoryOptional = categoryRepository.findById(id);
		ResponseData<CategoryResponse> responseData  = categoryOptional.isPresent() ? new ResponseData<CategoryResponse>(MessageConstants.USER_DELETE_SUCCESS, setData(categoryRepository.findById(id).get()), 200) : new ResponseData<CategoryResponse>(MessageConstants.CATEGORY_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) {
	    	Optional<Product> productOptional = Optional.ofNullable(productRepository.findByCategoryName(categoryOptional.get().getName()));
	    	if(productOptional.isPresent()) {Product product = productOptional.get(); product.setActive(0); productRepository.save(product);}
	    	Optional<SubCategory> subCategoryOptional = Optional.ofNullable(subCategoryRepository.findByCategoryName(categoryOptional.get().getName()));
	    	if(subCategoryOptional.isPresent()) {subCategoryRepository.deleteById(subCategoryOptional.get().getId());}
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
		List<ResponseData<Category>> responseDatas = new ArrayList<ResponseData<Category>>();
		categories.stream().forEach(elem -> responseDatas.add(setDataInResponse(elem)));
		return responseDatas;
	}
	
	/**
	 * Set data in response
	 * @param category
	 * @return
	 */
	private ResponseData<Category> setDataInResponse(Category category) {
		return new ResponseData<Category>(MessageConstants.CATEGORY_LIST_SUCCESS, category, 200);
	}
}