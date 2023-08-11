package com.bikkadit.electronicstore.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;

    //pending, Dispatched,Delivered
    //Enum cha wapar karun pn karu shakto
    private String orderStatus;

    //Not-paid,Paid
    //enum
    //boolean  false=Not-Paid true=Paid

    private String paymentStatus;

    private int orderAmount;

    @Column(length = 1000)
    private String billingAddress;

    private String billingName;

    private String billingPhone;

    private Date orderedDate;

    private Date deliveredDate;
//user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItems> orderItems=new ArrayList<>();


}
