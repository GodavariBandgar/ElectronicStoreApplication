package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.entities.CartItem;
import com.bikkadit.electronicstore.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private User user;
    private List<CartItem> items=new ArrayList<>();


}
