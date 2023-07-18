package com.bikkadit.electronicstore.dtos;

import com.bikkadit.electronicstore.entities.Cart;
import com.bikkadit.electronicstore.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private Product product;

    private int quantity;

    private int totalPrice;
}
