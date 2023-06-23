package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.*;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.payloads.ApiResponse;
import com.bikkadit.electronicstore.service.CategoryService;
import com.bikkadit.electronicstore.service.FileService;
import com.bikkadit.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ProductService productService;

    @Value("${category.profile.image.path}")
    private String imageUploadPath;

    /**
     * @Author Godavari Bandgar
     * @apiNote this method is a Create a Category
     * @param categoryDto
     * @return
     */

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("Request for save the Category Details");
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        logger.info("Request completed for save the Category Details");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }


    /**
     * @param categoryId
     * @param categoryDto
     * @return
     * @apiNote This method is use for update the category
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable("categoryId") String categoryId, @RequestBody CategoryDto categoryDto) {
        logger.info("Initiated Request for update the category details with categoryId:{}", categoryId);

        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
        logger.info("completed request for update the category details with categoryId:{}", categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }


    /**
     * @param categoryId
     * @return
     * @apiNote This method is used for delete the Category
     */


    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId) {

        logger.info("Initiated Request for delete the category details with:{}", categoryId);
        this.categoryService.deleteCategory(categoryId);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage(AppConstant.USER_DELETE);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        logger.info("Completed Request for delete the category details with:{}", categoryId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @param categoryId
     * @return
     * @apiNote This method is used for get single Category details
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategoryById(@PathVariable String categoryId) {
        logger.info("Initiated Request for get the single category details with:{}", categoryId);

        return new ResponseEntity<>(this.categoryService.getSingleCategoryById(categoryId), HttpStatus.OK);


    }


    /**
     * @return
     * @apiNote This api used for get AllCategory details
     */
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "9", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        logger.info("Initiated Request for get All category details ");
        return new ResponseEntity<>(categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);

    }

    //upload category image
    @PostMapping("/image/{categoryId}")
    public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId
    ) throws IOException {
        logger.info("Initiating Request for upload the image details with categoryId:{}",categoryId );
        String imageName = fileService.uploadFile(image, imageUploadPath);

        CategoryDto category = categoryService.getSingleCategoryById(categoryId);
        category.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(category, categoryId);

        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();

        logger.info("completed Request for upload the image details with categoryId:{}",categoryId );
        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);


    }

    //serve category image
    @GetMapping("/image/{categoryId}")
    public void serveCategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {

        CategoryDto category = categoryService.getSingleCategoryById(categoryId);
        logger.info("category image name:{}", category.getCoverImage());

        InputStream resource = fileService.getResource(imageUploadPath, category.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());

    }


    //  create product with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto) {

        ProductDto withCategory = productService.createWithCategory(dto, categoryId);

        return new ResponseEntity<>(withCategory,HttpStatus.CREATED);
    }




    }
