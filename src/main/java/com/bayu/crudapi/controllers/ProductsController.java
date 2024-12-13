package com.bayu.crudapi.controllers;

import com.bayu.crudapi.models.Product;
import com.bayu.crudapi.models.ProductDto;
import com.bayu.crudapi.services.ProductsRespository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRespository repository;

//    @GetMapping({"", "/"})
//    public String showProducts(Model model){
//        List<Product> products = repository.findAll();
//        model.addAttribute("products", products);
//        return "products/index";
//    }

    @GetMapping
    public ResponseEntity<Page<Product>> displayProducts(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = repository.findAll(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto){
        Product product = mapDTO(productDto);
        return new ResponseEntity<>(repository.save(product), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable int id, @Valid @RequestBody ProductDto productDto) {
        Optional<Product> existingProduct = repository.findById(id);

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            updateDataFromDTO(product, productDto);

            Product updatedProduct = repository.save(product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/addStock")
    public ResponseEntity<Product> addStock(@PathVariable int id, @RequestParam int amount){
        if (amount <= 0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();
        product.setStock(product.getStock() + amount);

        return new ResponseEntity<>(repository.save(product), HttpStatus.OK);
    }

    private Product mapDTO(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setCreatedAt(new Date());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.getStock());

        return product;
    }

    private void updateDataFromDTO(Product product, ProductDto productDto) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setStock(productDto.getStock());
    }


//    @GetMapping("/create")
//    public String displayCreate(Model model) {
//        ProductDto productDto = new ProductDto();
//        model.addAttribute("productDto", productDto);
//        return "products/CreateProduct";
//    }


//    @PostMapping("/create")
//    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
//
//        if (result.hasErrors()) {
//            return "products/CreateProduct";
//        }
//        Date createdAt = new Date();
//        Product product = new Product();
//        product.setName(productDto.getName());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setCategory(productDto.getCategory());
//        product.setCreatedAt(createdAt);
//
//        repository.save(product);
//        return "redirect:/products";
//    }



//    @GetMapping("/edit")
//    public String displayEdit(Model model, @RequestParam int id) {
//
//        try {
//            Product product = repository.findById(id).get();
//            model.addAttribute("product", product);
//
//            ProductDto productDto = new ProductDto();
//            productDto.setName(product.getName());
//            productDto.setDescription(product.getDescription());
//            product.setPrice(product.getPrice());
//            product.setCategory(productDto.getCategory());
//
//            model.addAttribute("productDto", productDto);
//
//        } catch (Exception exception) {
//            System.out.println("Exception : " + exception.getMessage());
//            return "redirect:/products";
//        }
//        return "products/EditProduct";
//    }
//

}
