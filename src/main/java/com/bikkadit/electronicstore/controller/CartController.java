package com.bikkadit.electronicstore.controller;

import com.bikkadit.electronicstore.dtos.AddItemToCartRequest;
import com.bikkadit.electronicstore.dtos.CartDto;
import com.bikkadit.electronicstore.payloads.ApiResponse;
import com.bikkadit.electronicstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    //add item to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId, @RequestBody AddItemToCartRequest request){
        CartDto cartDto = cartService.addItemToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart( @PathVariable String userId,@PathVariable int itemId){
      cartService.removeItemFromCart(userId, itemId);

        ApiResponse response = ApiResponse.builder()
                .message("item is removed!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //clear cart
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart( @PathVariable String userId) {
        cartService.clearCart(userId);

        ApiResponse response = ApiResponse.builder()
                .message("cart is clear in Db!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //add item to cart
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
}
