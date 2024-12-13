package com.bayu.crudapi.models;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderDto {

    @NotEmpty
    private List<CartDto> cartDtos;

    public List<CartDto> getCartDtos() {
        return cartDtos;
    }

    public void setCartDtos(List<CartDto> cartDtos) {
        this.cartDtos = cartDtos;
    }
}
