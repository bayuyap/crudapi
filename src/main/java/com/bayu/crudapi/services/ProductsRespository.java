package com.bayu.crudapi.services;

import com.bayu.crudapi.models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRespository extends JpaRepository<Product, Integer> {


}
