package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.Product;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.helper.Helper;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto create(ProductDto productDto) {

        //product Id
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        Product product = modelMapper.map(productDto, Product.class);

        //addedDate
        product.setAddedDate(new Date());
        Product saveproduct = productRepository.save(product);
        return modelMapper.map(saveproduct,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setLive(productDto.isLive());
        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.PRODUCT_DELETE));
        productRepository.delete(product);

    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase( "desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> page = productRepository.findAll(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;

    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase( "desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> page = productRepository.findByLiveTrue(pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase( "desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);

        PageableResponse<ProductDto> pageableResponse = Helper.getPageableResponse(page, ProductDto.class);
        return pageableResponse;
    }
}
