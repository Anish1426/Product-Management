package com.product.product_management.Controller;

import com.product.product_management.Service.GenericAPI.ApiRequest;
import com.product.product_management.Service.GenericAPI.ApiResponse;
import com.product.product_management.Entity.Product;
import com.product.product_management.Service.ProductService.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(Product.class);
    @Autowired
    private ProductService service;


    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ApiRequest<Product> products) {
        try {
            return new ResponseEntity<>(service.SaveAll(products), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while processing postAll request: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestParam int id, @RequestBody ApiRequest<Product> product) {
        try {
            return new ResponseEntity<>(service.updateProductById(id, product), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while processing updateProductDetails request: {}", e.getMessage());
            return new ResponseEntity<>(new ApiResponse<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(
            @RequestBody List<Integer> ids) {
        try {
             if (ids != null && !ids.isEmpty()) {
                return new ResponseEntity<>(service.deleteMultipleProducts(ids), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid parameters for product deletion", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Error occurred while processing deleteProducts request: {}", e.getMessage());
            return new ResponseEntity<>("Error processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProduct(
            @RequestParam(required = false) Map<String, String> filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            return new ResponseEntity<>(service.getAllProduct(filters, page, size, sortBy, sortDirection), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while processing getAllProduct request: {}", e.getMessage());
            throw new RuntimeException(e);

        }
    }
}
