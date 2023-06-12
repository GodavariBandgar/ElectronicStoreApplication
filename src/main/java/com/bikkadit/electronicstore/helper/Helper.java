package com.bikkadit.electronicstore.helper;

import com.bikkadit.electronicstore.dtos.PageableResponse;
import com.bikkadit.electronicstore.dtos.UserDto;
import com.bikkadit.electronicstore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPageableResponse(Page<U> page,Class<V> type) {     //U=Entity  V=Product Dto

        List<U> entity = page.getContent();
        //List<User> allUser = userRepository.findAll();

        List<V> dtoList = entity.stream().map(Object -> new ModelMapper().map(Object,type)).collect(Collectors.toList());


        PageableResponse<V> response= new PageableResponse<>();
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }


}
