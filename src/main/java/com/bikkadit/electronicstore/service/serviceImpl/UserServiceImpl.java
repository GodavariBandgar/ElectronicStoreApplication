package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronicstore.helper.AppConstant;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in string format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        logger.info("Initiating dao call for the save user details");
        User user = this.modelMapper.map(userDto, User.class);

        User saveUser = userRepository.save(user);

        logger.info("Completing dao call for the user details");
        UserDto newDto = this.modelMapper.map(saveUser, UserDto.class);

        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {

        logger.info("Initiating dao call for the update the user details with:{}",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        user.setName(userDto.getName());
        //user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImagename(userDto.getImagename());
        User updatedUser = userRepository.save(user);
        logger.info("Completing dao call for the update the user details with:{}",userId);
        return this.modelMapper.map(updatedUser, UserDto.class);
        

    }

    @Override
    public void deleteUser(String userId) {
        logger.info("Initiating dao call for the delete the user details with:{}",userId);


        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));

        logger.info("Completing dao call for the update the user details with:{}",userId);
        userRepository.delete(user);

    }

    @Override
    public List<UserDto> getAllUser(int pageNumber,int pageSize) {
        logger.info("Initiating dao call for the get all users");
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> page = userRepository.findAll(pageable);
        List<User> users = page.getContent();
        //List<User> allUser = userRepository.findAll();

        List<UserDto> userDtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completing dao call for get All users");
        return userDtos;
    }

    @Override
    public UserDto getUserById(String userId) {
        logger.info("Initiating dao call for the get the single user details with:{}",userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completed dao call for the get the single user details with:{}",userId);
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.info("Initiating dao call for the get the single user details with:{}",email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstant.NOT_FOUND));
        logger.info("Completed dao call for the get the single user details with:{}",email);
        return this.modelMapper.map(user, UserDto.class);

    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        logger.info("Initiating dao call for the get the single user details with:{}",keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);

        List<UserDto> dtos = users.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        logger.info("Completed dao call for the get the single user details with:{}",keyword);
        return dtos;
    }

    private UserDto entityToDto(User savedUser){
        UserDto userDto=UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .gender(savedUser.getGender())
                .imagename(savedUser.getImagename())
                .build();

        return userDto;

    }
    private User dtoToEntity(UserDto userDto){
        User user=User.builder()
                .userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .gender(userDto.getGender())
                .imagename(userDto.getImagename())
                .build();

        return user;
    }
}
