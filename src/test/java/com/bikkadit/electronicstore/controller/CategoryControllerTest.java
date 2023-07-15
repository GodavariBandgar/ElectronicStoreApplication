package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;

import com.bikkadit.electronicstore.entities.Category;


import com.bikkadit.electronicstore.service.CategoryService;

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

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @MockBean
    private CategoryService categoryService;
    private Category category;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){

        category= Category.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();
    }


    @Test
    void createCategory() throws Exception{

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategory(Mockito.any())).thenReturn(categoryDto);
        //actual request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(category))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void updateCategory() throws Exception{

        String categoryId ="143";
        category= Category.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();

        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.updateCategory(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/category/" +categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(categoryDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void deleteCategory() throws Exception{

        String categoryId= "2365";
        Mockito.doNothing().when(categoryService).deleteCategory(Mockito.anyString());
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/category/" +categoryId))
                .andDo(print())
                .andExpect(status().isOk());
        //verify
        Mockito.verify(categoryService,Mockito.times(1)).deleteCategory(categoryId);

    }

    @Test
    void getSingleCategoryById()throws Exception {

        String categoryId ="123";

        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
//        Mockito.when(categoryService.getCategoryById(Mockito.anyString())).thenReturn(categoryDto);
        Mockito.when(categoryService.getSingleCategoryById(categoryId)).thenReturn(categoryDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/category/"+categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    void getAllCategory() throws  Exception{

         CategoryDto categoryDto1= CategoryDto.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();

        CategoryDto categoryDto2= CategoryDto.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();

        CategoryDto categoryDto3= CategoryDto.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();

        PageableResponse<CategoryDto> pageableResponse= new PageableResponse<>();

        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(categoryDto1,categoryDto2,categoryDto3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(categoryService.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }





    private String convertObjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}