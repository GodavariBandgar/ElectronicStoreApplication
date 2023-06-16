package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.ImageResponse;
import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.payloads.ApiResponse;
import com.bikkadit.electronicstore.service.FileService;
import com.bikkadit.electronicstore.service.UserService;
import org.aspectj.bridge.IMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

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
     * @apiNote This method is used for get single User
     * @param userId
     * @return
     */

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId){
        logger.info("Initiated Request for get the single user details with userId:{}",userId);

        return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);



    }

    /**
     * @apiNote This api used for get AllUsers
     * @return
     */

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>>getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "9",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)


    {
        logger.info("Initiated Request for get All user details ");
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);

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
    //upload User image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId
    ) throws IOException {

        String imageName = fileService.uploadFile(image, imageUploadPath);

        UserDto user = userService.getUserById(userId);
        user.setImagename(imageName);
        UserDto userDto = userService.updateUser(user,userId);

        ImageResponse imageResponse=ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);




    }

    //serve User image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException{

        UserDto user = userService.getUserById(userId);
        logger.info("User image name:{}",user.getImagename());

        InputStream resource = fileService.getResource(imageUploadPath, user.getImagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());

    }






}
