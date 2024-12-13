package com.bayu.crudapi.models;

import jakarta.validation.constraints.*;

public class ProductDto {

    @NotEmpty(message = "This is required!")
    private String name;

    @NotEmpty(message = "This is required!")
    private String category;

    @Min(0)
    private int price;

    @Size(min = 10, message = "Description must at least 10 characters!")
    @Size(max = 1500, message = "Description cannot exceed 1500 characters!")
    private String description;

    @Min(1)
    private int stock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
