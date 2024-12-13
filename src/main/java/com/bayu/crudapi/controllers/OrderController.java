package com.bayu.crudapi.controllers;

import com.bayu.crudapi.models.Cart;
import com.bayu.crudapi.models.Order;
import com.bayu.crudapi.models.OrderDto;
import com.bayu.crudapi.models.Product;
import com.bayu.crudapi.services.CartRepository;
import com.bayu.crudapi.services.OrderRepository;
import com.bayu.crudapi.services.ProductsRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private CartRepository cartRepository;


    @Autowired
    private ProductsRespository productsRespository;

    @PostMapping
    public ResponseEntity<Order> orderItem(@Valid @RequestBody OrderDto dto) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("PROCESSED");

        List<Cart> cartList = dto.getCartDtos().stream().map(cartDto -> {

            Product product = productsRespository.findById(cartDto.getIdProduct())
                            .orElseThrow(() -> new RuntimeException("Product Not Found"));

            if (product.getStock() < cartDto.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " +product.getName()+ "!"
                        + product.getName() +" stock is " + product.getStock());
            }
            product.setStock(product.getStock() - cartDto.getQuantity());
            productsRespository.save(product);

            Cart cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(cartDto.getQuantity());
            cart.setOrder(order);

            return cart;
        }).toList();
        int total = dto.getCartDtos().stream()
                        .mapToInt(cartDto -> {
                            Product product = productsRespository.findById(cartDto.getIdProduct())
                                    .orElseThrow(() -> new RuntimeException("Product Not Found"));
                            return product.getPrice() * cartDto.getQuantity();
                        }).sum();

        order.setTotalPrice(total);

        order.setCartList(cartList);

        cartRepository.deleteAll();
        return new ResponseEntity<>(repository.save(order), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

}
