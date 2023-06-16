package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.Category;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        logger.info("Initiating dao call for the save the Category details");
        Category category = this.modelMapper.map(categoryDto, Category.class);

        Category saveCategory = categoryRepository.save(category);

        logger.info("Completing dao call for the category details");
        CategoryDto categoryDto1 = this.modelMapper.map(saveCategory, CategoryDto.class);

        return categoryDto1;



    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {

        logger.info("Initiating dao call for the update the category details with:{}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        logger.info("Completing dao call for the update the category details with:{}",categoryId);
        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(String categoryId) {

        logger.info("Initiating dao call for the delete the category details with:{}",categoryId);


        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completing dao call for the update the category details with:{}",categoryId);
        categoryRepository.delete(category);


    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao call for the get all categories");
        Sort sort = (sortDir.equalsIgnoreCase( "desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Category> page = categoryRepository.findAll(pageable);
        logger.info("Completed dao call for the get all categories");
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto getSingleCategoryById(String categoryId) {
        logger.info("Initiating dao call for the get the single category details with:{}",categoryId);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completed dao call for the get the single category details with:{}",categoryId);
        return this.modelMapper.map(category, CategoryDto.class);
    }


}
