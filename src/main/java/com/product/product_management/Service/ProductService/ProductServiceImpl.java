package com.product.product_management.Service.ProductService;
import com.product.product_management.Service.GenericAPI.ApiRequest;
import com.product.product_management.Service.GenericAPI.ApiResponse;
import com.product.product_management.Entity.Product;
import com.product.product_management.Repository.ProductRepository;
import com.product.product_management.DbSequence.SequenceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SequenceImpl sequence;


    private static final Logger logger = LoggerFactory.getLogger(Product.class);
    Date date = new Date();


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ApiResponse<Product> SaveAll(ApiRequest<Product> request) {

        ApiResponse<Product> response = new ApiResponse<>();
        try {
            List<Product> products = request.getData();
            products.forEach(product -> product.setId(sequence.getSequenceNumber(Product.SEQUENCE_NAME)));
            response.setTimeStamp(date);
            response.setSuccess(true);
            response.setMessage("Products Saved Successfully......");
            response.setData(productRepository.saveAll(products));
            response.setCount(Long.valueOf(products.size()));
            logger.info("All Product are  Saved successful....");
            return response;
        } catch (DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Products failed to add.....");
            logger.error("Products failed to add.....");
            return response;
        }
    }

    @Override
    public ApiResponse<List<Product>> getAllProduct() {
        ApiResponse<List<Product>> response = new ApiResponse<>();
        try {
            List<Product> allProduct = productRepository.findAll();
            response.setTimeStamp(date);
            response.setSuccess(true);
            response.setMessage("Getting the product data's");
            response.setData(allProduct);
            response.setCount(productRepository.count());
            return response;
        }
        catch (DataAccessException e){
            response.setTimeStamp(date);
            response.setSuccess(false);
            response.setMessage("Error in the getting data");
            logger.error("Error in the getting data");
            return response;
        }
    }


    @Override
    public String deleteMultipleProducts(List<Integer> ids) {
            for (Integer id : ids) {
                if (productRepository.existsById(id)) {
                    productRepository.deleteAllById(ids);
                    logger.info("Products with IDs " + ids + " are successfully deleted " + date);
                    break;
                }
                else {
                    logger.info("Products with IDs " + ids + " is not found  " + date);
                    return "Products with IDs " + ids + " is not found  ";
                }

            }
        return "Products with IDs " + ids + " are successfully deleted ";
    }

    @Override
    public ApiResponse<Product> updateProductById(int id, ApiRequest<Product> request) {
        ApiResponse<Product> response = new ApiResponse<>();
        try {
            List<Product> products = request.getData();
            Product updatedProduct = products.get(0);

            if (productRepository.existsById(id)) {
                updatedProduct.setId(id);
                response.setTimeStamp(date);
                response.setSuccess(true);
                response.setMessage("The product with ID: " + id + " has been updated successfully");
                response.setData(productRepository.save(updatedProduct));
                logger.info("The product with ID: " + id + " has been updated successfully");
                return response;
            } else {
                response.setSuccess(false);
                response.setTimeStamp(date);
                response.setMessage("The product with ID: " + id + " does not exist");
                return response;

            }
        } catch (DataAccessException e) {
            logger.error("The product updating failed", e);
            response.setSuccess(false);
            response.setMessage("The product with ID: " + id + " has been updated failed ");
            return response;

        }


    }

    public ApiResponse<Page<Product>> getAllProduct(Map<String, String> filters, int page, int size, String sortBy, String sortDirection) {
        ApiResponse<Page<Product>> response = new ApiResponse<>();

        try {
            Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, sortBy);
            Pageable pageable = PageRequest.of(page, size, sort);

            Page<Product> products;
            if (filters != null && !filters.isEmpty()) {
                Map.Entry<String, String> entry = filters.entrySet().iterator().next();
                products = (!isPageKey(entry.getKey())) ?
                        productRepository.findByFilter(entry.getKey(), entry.getValue(), pageable) :
                        productRepository.findAll(pageable);
                response.setSuccess(true);
                response.setMessage("Product retrieved successfully");
                response.setData(products);
                logger.info("Product retrieved successfully");
            }


            else {
                ApiResponse<List<Product>> product = this.getAllProduct();
                response.setSuccess(true);
                response.setMessage("All Products retrieved successfully");
                response.setData(product);
                logger.info("All Products retrieved successfully");
            }

        } catch (DataAccessException e) {
            logger.error("Error while retrieving products", e);
            response.setSuccess(false);
            response.setMessage("Error while retrieving products");
        }


        response.setTimeStamp(date);



        return response;

    }

    @Override
    public ApiResponse<List<Product>> getProductById(int id) {
        ApiResponse<List<Product>> response = new ApiResponse<>();
        try {
            if(productRepository.existsById(id)){
                response.setData(productRepository.findById(id));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }


    private boolean isPageKey(String key) {
        return key.equals("page") || key.equals("size") || key.equals("sortBy") || key.equals("sortDirection");
    }
}
