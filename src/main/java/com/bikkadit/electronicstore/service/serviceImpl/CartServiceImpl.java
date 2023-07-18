package com.bikkadit.electronicstore.service.serviceImpl;

import com.bikkadit.electronicstore.dtos.AddItemToCartRequest;
import com.bikkadit.electronicstore.dtos.CartDto;
import com.bikkadit.electronicstore.entities.Cart;
import com.bikkadit.electronicstore.entities.CartItem;
import com.bikkadit.electronicstore.entities.Product;
import com.bikkadit.electronicstore.entities.User;
import com.bikkadit.electronicstore.exceptions.BadApiRequestException;
import com.bikkadit.electronicstore.exceptions.ResourceNotFoundException;
import com.bikkadit.electronicstore.repository.CartItemRepository;
import com.bikkadit.electronicstore.repository.CartRepository;
import com.bikkadit.electronicstore.repository.ProductRepository;
import com.bikkadit.electronicstore.repository.UserRepository;
import com.bikkadit.electronicstore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity<=0){
            throw new BadApiRequestException("Requested quantity is not valid!!");
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product Not Found in Database!!"));
        //fetch the user from DB

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found in Database!!"));
        Cart cart = null;

        try{
            cart = cartRepository.findByUser(user).get();

        }catch(NoSuchElementException e){

            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());

            cart.setCreatedAt(new Date());

        }

        //perform cart operations
        //if cart item already present then update
        AtomicReference<Boolean> updated=new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updeteditems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item  already present in cart
            item.setQuantity(quantity);
            item.setTotalPrice(quantity*product.getPrice());
            updated.set(true);

            }

            return item;
        }).collect(Collectors.toList());
        cart.setItems(updeteditems);
        //create items
       if (!updated.get()){
           CartItem cartItems = CartItem.builder()
                   .quantity(quantity)
                   .totalPrice(quantity * product.getPrice())
                   .cart(cart)
                   .product(product)
                   .build();
           cart.getItems().add(cartItems);
       }

        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);

        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("CartItem Not Found in DB"));
        cartItemRepository.delete(cartItem1);



    }

    @Override
    public void clearCart(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found in Database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found!!"));
        cart.getItems().clear();
        cartRepository.save(cart);


    }

    @Override
    public CartDto getCartByUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found in Database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found!!"));

        return mapper.map(cart,CartDto.class);
    }
}
