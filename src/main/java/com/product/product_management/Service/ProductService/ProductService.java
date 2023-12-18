package com.product.product_management.Service.ProductService;

import com.product.product_management.Service.GenericAPI.ApiRequest;
import com.product.product_management.Service.GenericAPI.ApiResponse;
import com.product.product_management.Entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    ApiResponse<List<Product>> getAllProduct();
    String deleteMultipleProducts(List<Integer> id);
    ApiResponse<Product> updateProductById(int id,ApiRequest<Product> request);
    ApiResponse<Product> SaveAll(ApiRequest<Product> request);
    ApiResponse<Page<Product>> getAllProduct(Map<String, String> filters, int page, int size, String sortBy, String sortDirection);

    ApiResponse<List<Product>> getProductById(int id);
}
