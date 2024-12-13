package com.bayu.crudapi.services;

import com.bayu.crudapi.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRespository extends JpaRepository<Product, Integer> {


}
