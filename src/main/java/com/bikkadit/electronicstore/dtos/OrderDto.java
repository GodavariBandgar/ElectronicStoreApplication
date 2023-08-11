package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.entities.OrderItems;
import com.bikkadit.electronicstore.entities.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private int orderAmount;
    private String billingAddress;
    private String billingName;
    private String billingPhone;
    private Date orderedDate=new Date();
    private Date deliveredDate;
    //user
    private User user;
    private List<OrderItemDto> orderItems=new ArrayList<>();


}
