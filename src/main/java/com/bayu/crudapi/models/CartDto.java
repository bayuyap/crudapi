package com.bayu.crudapi.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartDto {

    @NotNull
    private int idProduct;

    @Min(1)
    private int quantity;

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
