package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.MockedConstructionImpl;
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
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    User user;

    @BeforeEach
    public void init(){
        user=User.builder().name("Godavari")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();
    }

    @Test
    void createUser() {
        //arrange
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        //Act
        UserDto user1 = userService.createUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);

        //Assert
        Assertions.assertEquals("Godavari",user1.getName());

    }



    @Test
    void updateUser() {
        String userId ="abcd";
        UserDto userDto=UserDto.builder()
                .name("Godavari Bandgar")
                .about("This is updated user details")
                .gender("Female")
                .imagename("xyz.png")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updatedUser = userService.updateUser(userDto, userId);
        System.out.println(updatedUser.getName());
        Assertions.assertNotNull(userDto);



    }

    @Test
    void deleteUser() {

        String userId="userIdabc";
        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }

    @Test
    void getAllUser() {

      User  user1=User.builder().name("Dilip")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();
      User  user2=User.builder().name("Pari")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();

        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);


        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1, 2, "name", "asc");
        Assertions.assertEquals(3,allUser.getContent().size());


    }

    @Test
    void getUserById() {
        String userId="goda12";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //actual call for Service method
        UserDto userDto = userService.getUserById(userId);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"name not matched");
    }

    @Test
    void getUserByEmail() {

        String emailId="bandgar@gmail.com";

        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(emailId);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email Not matched");
    }

    @Test
    void searchUser() {
         User user1=User.builder().name("Dilip")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();

       User user2=User.builder().name("Pari Devkatte")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();
        User user3 =User.builder().name("Godavari")
                .email("bandgar@gmail.com")
                .about("This is testing create user")
                .gender("Female")
                .imagename("abc.png")
                .password("goda")
                .build();

        String Keywords="Pari";

        Mockito.when(userRepository.findByNameContaining(Keywords)).thenReturn(List.of(user,user1,user2,user3));

        List<UserDto> userDtos = userService.searchUser((Keywords));

        Assertions.assertEquals(4,userDtos.size(),"Size not matched!!!");


    }
}