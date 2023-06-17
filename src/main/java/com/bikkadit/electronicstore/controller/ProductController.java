package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.payloads.ApiResponse;
import com.bikkadit.electronicstore.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    Logger logger= LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        logger.info("Request for save the Product Details");
        ProductDto productDto1 = this.productService.create(productDto);
        logger.info("Request completed for save the Product Details");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @PathVariable("productId") String productId, @RequestBody ProductDto productDto){
        logger.info("Initiated Request for update the product details with productId:{}",productId);

        ProductDto updatedProductDto = this.productService.update(productDto, productId);
        logger.info("completed request for update the product details with productId:{}",productId);
        return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable String productId){

        logger.info("Initiated Request for delete the product details with productId:{}",productId);
        this.productService.delete(productId);

        ApiResponse apiResponse = ApiResponse.builder().message(AppConstant.PRODUCT_DELETE).status(HttpStatus.OK).success(true).build();

        logger.info("Completed Request for delete the product details with productId:{}",productId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct (@PathVariable String productId) {
        logger.info("Initiated Request for delete the product details with productId:{}",productId);
        ProductDto productDto = this.productService.get(productId);
        logger.info("Completed Request for delete the product details with productId:{}",productId);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>>getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "9",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)


    {
        logger.info("Initiated Request for get All product details ");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);



    }
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "9",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
        logger.info("Initiated Request for get All Product details ");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }

    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>>searchProduct(
            @PathVariable String Query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "9",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir) {
        logger.info("Initiated Request for get All Product details ");
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(Query,pageNumber, pageSize, sortBy, sortDir);

        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);

    }















    }
