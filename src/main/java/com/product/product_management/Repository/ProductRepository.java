package com.product.product_management.Repository;

import com.product.product_management.Entity.Product;

import com.product.product_management.Service.ProductService.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,Integer> {
    @Query("{ ?0:?1}")
    Page<Product> findByFilter(String key, String value, Pageable pageable);

}
