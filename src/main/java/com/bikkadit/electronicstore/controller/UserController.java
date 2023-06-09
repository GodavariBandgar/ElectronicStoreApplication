package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.payloads.ApiResponse;
import com.bikkadit.electronicstore.service.UserService;
import org.aspectj.bridge.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @Author Godavari Bandgar
     * @apiNote this method is a Create a User
     * @param userDto
     * @return
     */

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        logger.info("Request for save the User Details");
        UserDto dto = this.userService.createUser(userDto);
        logger.info("Request completed for save the User Details");
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    /**
     * @apiNote This method is use for update the User
     * @param uid
     * @param userdto
     * @return
     */

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String uid, @RequestBody UserDto userdto){
        logger.info("Initiated Request for update the user details with userId:{}",uid);

        UserDto updatedUserDto = this.userService.updateUser(userdto, uid);
        logger.info("completed request for update the user details with userId:{}",uid);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    /**
     *
     * @apiNote This method is used for delete the User
     * @param userId
     * @return
     */

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId){

        logger.info("Initiated Request for delete the user details with userId:{}",userId);
        this.userService.deleteUser(userId);

        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(AppConstant.USER_DELETE);
        apiResponse.setSuccess(true);
        apiResponse.setStatus(HttpStatus.OK);

        logger.info("Completed Request for delete the user details with userId:{}",userId);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    /**
     * @apiNote This method is used for gwt single User
     * @param userId
     * @return
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        logger.info("Initiated Request for get the single user details with userId:{}",userId);

        logger.info("Completed Request for get the single user details with userId:{}",userId);
        return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);

    }

    /**
     * @apiNote This api used for get AllUsers
     * @return
     */

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
        logger.info("Initiated Request for get All user details ");
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);

    }

    /**
     * @apiNote This method is used for get user by email
     * @param email
     * @return
     */

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        logger.info("Initiated Request for get the single user details with userId:{}",email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    /**
     * @apiNote This method is used for search user by using keyword
     * @param keywords
     * @return
     */

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUsers(@PathVariable String keywords){
        logger.info("Initiated Request for get the single user details with userId:{}",keywords);
        return new ResponseEntity<>(userService.searchUser(keywords), HttpStatus.OK);

    }

}
