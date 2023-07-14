package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.Product;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.ProductService;
import com.bikkadit.electronicstore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    Product product;

    @BeforeEach
    public void init(){
        product=Product.builder().title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();

   }
    @Test
    void create() {
        //arrange
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        //Act
        ProductDto productDto = productService.create(modelMapper.map(product, ProductDto.class));
        System.out.println(productDto.getTitle());
        Assertions.assertNotNull(productDto);

        //Assert
        Assertions.assertEquals("Laptop",productDto.getTitle());



    }

    @Test
    void update() {

        String productId ="abcd";
        ProductDto productDto=ProductDto.builder().title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto updated = productService.update(productDto, productId);
        System.out.println(updated.getTitle());
        Assertions.assertNotNull(productDto);

    }

    @Test
    void delete() {

        String productId="4512";
        Mockito.when(productRepository.findById("4512")).thenReturn(Optional.of(product));
        productService.delete(productId);
        Mockito.verify(productRepository,Mockito.times(1)).delete(product);


    }

    @Test
    void get() {


    }

    @Test
    void getAll() {
       Product product=Product.builder().title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();
        Product product1=Product.builder().title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();
        Product product2=Product.builder().title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();

        List<Product> productList= Arrays.asList(product,product1,product2);
        Page<Product> page=new PageImpl<>(productList);


        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> pageableResponse = productService.getAll(1, 2, "name", "asc");
        Assertions.assertEquals(3,pageableResponse.getContent().size());

    }
}