package com.bikkadit.electronicstore.service;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.ProductDto;

public interface ProductService {
    //create
    ProductDto create(ProductDto productDto);

    //update

    ProductDto update(ProductDto productDto,String productId);

    //delete

    void delete(String productId);

    //get single
    ProductDto get(String productId);

    //get All
    PageableResponse<ProductDto>getAll(int pageNumber,int pageSize,String sortBy,String sortDir);


    //get All live
    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);


    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);





}
