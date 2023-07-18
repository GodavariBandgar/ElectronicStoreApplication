package com.bikkadit.electronicstore.dtos;
import com.bikkadit.electronicstore.entities.Product;
import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private Product product;

    private int quantity;

    private double totalPrice;
}
