package com.bayu.crudapi.controllers;


import com.bayu.crudapi.models.Cart;
import com.bayu.crudapi.models.CartDto;
import com.bayu.crudapi.models.Product;
import com.bayu.crudapi.services.CartRepository;
import com.bayu.crudapi.services.ProductsRespository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartRepository repository;

    @Autowired
    private ProductsRespository productsRespository;

    @PostMapping
    public ResponseEntity<Cart> addProductstoCart(@Valid @RequestBody CartDto dto) {
        Optional<Product> product = productsRespository.findById(dto.getIdProduct());

        if (product.isPresent()) {
            Optional<Cart> existingItem = repository.findAll().stream()
                    .filter(cart -> cart.getProduct().getId() == dto.getIdProduct())
                    .findFirst();

            if (existingItem.isPresent()){
                Cart cart = existingItem.get();
                cart.setQuantity(cart.getQuantity() + dto.getQuantity());
                return new ResponseEntity<>(repository.save(cart), HttpStatus.OK);
            } else {
                Cart cart = new Cart();
                cart.setProduct(product.get());
                cart.setQuantity(dto.getQuantity());

                return new ResponseEntity<>(repository.save(cart), HttpStatus.CREATED);
            }

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Cart>> getAllItems() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
