package com.bikkadit.electronicstore.controller;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.service.UserService;

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
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @MockBean
    private UserService userService;
    private User user;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        user=User.builder()
                .name("Godavari")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();



    }
   @Test
  public void createUser()throws Exception  {
       //users+Post + user data as json
       //data as jso+status created
       UserDto dto = modelMapper.map(user, UserDto.class);
       Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);
       //actual request for url
       this.mockMvc.perform(
                       MockMvcRequestBuilders.post("/users")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(convertObjectToJsonString(dto))
                               .accept(MediaType.APPLICATION_JSON))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.name").exists());

   }

    @Test
    void updateUser()throws Exception  {

        //User/{userId}  +PUT request +json
        String userId ="143";
        user=User.builder().name("Godavari")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/" +userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(userDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void deleteUser()throws Exception {

        String userId= "2365";
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/users/" +userId))
                .andDo(print())
                .andExpect(status().isOk());
        //verify
        Mockito.verify(userService,Mockito.times(1)).deleteUser(userId);



    }

    @Test
    void getUser()throws Exception {

        String userId ="123";

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(userDto);
        Mockito.when(userService.getUserById(userId)).thenReturn(userDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/"+userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void getAllUsers()throws Exception {
        UserDto userDto1 = UserDto.builder()
                .name("Pari ")
                .email("pari@gmail.com")
                .password("pari143")
                .gender("female")
                .about("Testing method for getting all user")
                .imagename("abc.png")
                .build();
        UserDto userDto2 = UserDto.builder()
                .name("Dilip")
                .email("dilip@gmail.com")
                .password("dilip145")
                .gender("Male")
                .about("Testing method for getting all user")
                .imagename("xyz.png")
                .build();
        UserDto userDto3 = UserDto.builder()
                .name("Tuka Bandgar")
                .email("tuka@gmail.com")
                .password("tuka156")
                .gender("male")
                .about("Testing method for getting all user")
                .imagename("xyz.png")
                .build();

        PageableResponse<UserDto> pageableResponse= new PageableResponse<>();

        pageableResponse.setLastPage(false);
        pageableResponse.setTotalElements(2000);
        pageableResponse.setPageNumber(50);
        pageableResponse.setContent(Arrays.asList(userDto1,userDto2,userDto3));
        pageableResponse.setTotalPages(200);
        pageableResponse.setPageSize(20);

        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        //request for url
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUserByEmail() throws Exception {

        String emailId="kirti@gmail.com";
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(userDto);
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/email/"+emailId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void searchUsers()throws Exception {

        String keyword= "Bandgar";
        UserDto userDto = UserDto.builder()
                .name("Pari ")
                .email("pari@gmail.com")
                .password("pari143")
                .gender("female")
                .about("Testing method for getting all user")
                .imagename("abc.png")
                .build();
        UserDto userDto2 = UserDto.builder()
                .name("Dilip")
                .email("dilip@gmail.com")
                .password("dilip145")
                .gender("Male")
                .about("Testing method for getting all user")
                .imagename("xyz.png")
                .build();
        UserDto userDto3 = UserDto.builder()
                .name("Tuka Bandgar")
                .email("tuka@gmail.com")
                .password("tuka156")
                .gender("male")
                .about("Testing method for getting all user")
                .imagename("xyz.png")
                .build();
        Mockito.when(userService.searchUser(keyword)).thenReturn(List.of(userDto3,userDto2,userDto));
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/users/search/"+keyword)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertObjectToJsonString(Object user){

        try{

            return new ObjectMapper().writeValueAsString(user);

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}