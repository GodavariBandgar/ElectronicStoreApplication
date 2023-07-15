package com.bikkadit.electronicstore.controller;
import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.ProductDto;
import com.bikkadit.electronicstore.entities.Product;

import com.bikkadit.electronicstore.service.ProductService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @MockBean
    private ProductService productService;
    private Product product;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        product=Product.builder()
                .title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();
    }

    @Test
    void createProduct() throws Exception{

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.create(Mockito.any())).thenReturn(productDto);
        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());



    }

    @Test
    void updateProduct() throws Exception {

        String productId ="143";
        product=Product.builder()
                .title("Laptop")
                .description("This is laptop for Hp")
                .price(40000.00)
                .discountedPrice(1000)
                .quantity(4000)
                .live(true)
                .stock(true)
                .build();

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.update(Mockito.any(),Mockito.anyString())).thenReturn(productDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/products/" +productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(productDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());


    }

    @Test
    void deleteProduct() throws Exception {

        String productId= "2365";
        Mockito.doNothing().when(productService).delete(Mockito.anyString());
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/products/" +productId))
                .andDo(print())
                .andExpect(status().isOk());
        //verify
        Mockito.verify(productService,Mockito.times(1)).delete(productId);



    }

    @Test
    void getProduct() {




    }

    @Test
    void getAllProduct() {
    }
    private String convertObjectToJsonString(Object product){

        try{

            return new ObjectMapper().writeValueAsString(product);

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}