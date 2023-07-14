package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.CategoryDto;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.Category;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.repository.CategoryRepository;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.CategoryService;
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
class CategoryServiceImplTest {


    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @BeforeEach
    public void init(){

       category= Category.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();
    }

    @Test
    void createCategory() {
        //arrange
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);

        //Act
        CategoryDto category1 = categoryService.createCategory(modelMapper.map(category, CategoryDto.class));
        System.out.println(category1.getTitle());
        Assertions.assertNotNull(category1);

        //Assert
        Assertions.assertEquals("Mobiles",category1.getTitle());

    }

    @Test
    void updateCategory() {

        String categoryId ="1234";
       CategoryDto categoryDto= CategoryDto.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();

        Mockito.when(categoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepository.save(Mockito.any())).thenReturn(category);
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
        System.out.println(updatedCategory.getTitle());
        Assertions.assertNotNull(categoryDto);


    }

    @Test
    void deleteCategory() {

        String categoryId="1245";
        Mockito.when(categoryRepository.findById("1245")).thenReturn(Optional.of(category));
        categoryService.deleteCategory(categoryId);
        Mockito.verify(categoryRepository,Mockito.times(1)).delete(category);

    }

    @Test
    void getAllCategory() {
       Category category= Category.builder()
                .title("Mobiles")
                .description("This is Redmi phone")
                .coverImage("abc.png")
                .build();
        Category category1= Category.builder()
                .title("Laptop")
                .description("This is Hp laptop")
                .coverImage("abc.png")
                .build();
         Category category2= Category.builder()
                .title("Books")
                .description("This is History Book")
                .coverImage("abc.png")
                .build();
        List<Category> list = Arrays.asList(category, category1, category2);
        Page<Category> page=new PageImpl<>(list);


        Mockito.when(categoryRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(1, 2, "title", "asc");
        Assertions.assertEquals(3,allCategory.getContent().size());



    }

    @Test
    void getSingleCategoryById() {

        String categoryId="goda12";
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        //actual call for Service method
        CategoryDto categoryDto = categoryService.getSingleCategoryById(categoryId);

        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle(),"name not matched");

    }
}