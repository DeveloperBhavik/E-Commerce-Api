package com.e_commerce_app.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_commerce_app.entity.Product;
import com.e_commerce_app.entity.SubCategory;
import com.e_commerce_app.repository.ProductRepository;
import com.e_commerce_app.repository.SubCategoryRepository;
import com.e_commerce_app.request.SubCategoryRequest;
import com.e_commerce_app.response.ResponseData;
import com.e_commerce_app.response.SubCategoryResponse;
import com.e_commerce_app.service.SubCategoryService;
import com.e_commerce_app.utils.MessageConstants;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * Save sub-category
	 */
	@Override
	public ResponseData<SubCategoryResponse> saveSubCategory(SubCategoryRequest subCategoryRequest) {
		
		String message = null;
		message = (subCategoryRequest.getId() != null ? MessageConstants.SUB_CATEGORY_UPDATE_SUCCESS : MessageConstants.SUB_CATEGORY_SAVE_SUCCESS);
		SubCategory subCategory = new SubCategory();
		subCategory.setId(subCategoryRequest.getId() != null ? subCategoryRequest.getId() : null);
		subCategory.setName(subCategoryRequest.getName());
		subCategory.setCategory(subCategoryRequest.getCategory());
		subCategoryRepository.save(subCategory);
		return new ResponseData<SubCategoryResponse>(message, setData(subCategory), 200);
	}
	
	/**
	 * Set data in response
	 * @param subCategory
	 * @return
	 */
	private SubCategoryResponse setData(SubCategory subCategory) {
		
		SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
		subCategoryResponse.setId(subCategory.getId());
		subCategoryResponse.setName(subCategory.getName());
		subCategoryResponse.setCategory(subCategory.getCategory());
		return subCategoryResponse;
	}
	
	/**
	 * Check sub-category name existence
	 */
	@Override
	public SubCategory checkSubCategoryExistence(String name) {
		return subCategoryRepository.findByName(name);
	}

	/**
	 * Get sub-category by id
	 */
	@Override
	public ResponseData<SubCategoryResponse> getSubCategoryById(Long id) {
		
		Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
		ResponseData<SubCategoryResponse> responseData  = subCategoryOptional.isPresent() ? new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_FETCH_SUCCESS, setData(subCategoryOptional.get()), 200) : new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_NOT_FOUND, null, 420);
		return responseData;
	}

	/**
	 * Delete sub-category by id
	 */
	@Override
	public ResponseData<SubCategoryResponse> deleteSubCategoryById(Long id) {
		
		Optional<SubCategory> subCategoryOptional = subCategoryRepository.findById(id);
		ResponseData<SubCategoryResponse> responseData  = subCategoryOptional.isPresent() ? new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_DELETE_SUCCESS, setData(subCategoryRepository.findById(id).get()), 200) : new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_NOT_FOUND, null, 420);
	    if(responseData.getStatus() == 200) { 
	    	Optional<Product> productOptional = Optional.ofNullable(productRepository.findBySubCategoryName(subCategoryOptional.get().getName()));
	    	if(productOptional.isPresent()) {Product product = productOptional.get(); product.setActive(0); productRepository.save(product);}
	    	subCategoryRepository.deleteById(id);
	    }
		return responseData;
	}
	
	/**
	 * Get list of all sub-categories
	 */
	@Override
	public Object getListOfAllSubCategories() {
		
		List<SubCategory> subCategories = (List<SubCategory>) subCategoryRepository.findAll();
		List<ResponseData<SubCategoryResponse>> responseDatas = new ArrayList<ResponseData<SubCategoryResponse>>();
		subCategories.stream().forEach(elem -> responseDatas.add(new ResponseData<SubCategoryResponse>(MessageConstants.SUB_CATEGORY_LIST_SUCCESS, setData(elem), 200)));
		return responseDatas;
	}
}