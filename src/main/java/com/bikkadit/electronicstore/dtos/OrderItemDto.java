package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.entities.Order;
import com.bikkadit.electronicstore.entities.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private int orderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;
}
