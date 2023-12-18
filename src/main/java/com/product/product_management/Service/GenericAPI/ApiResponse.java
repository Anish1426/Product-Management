package com.product.product_management.Service.GenericAPI;

import com.product.product_management.Entity.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.Date;

@Getter
@Setter
public class ApiResponse<T>  {
    private Date timeStamp;
    private boolean success;
    private  String message;
    private  Long count;
    private Object data;


    public ApiResponse() {
        this.timeStamp=null;
        this.success = true;
        this.message =null;
        this.count = null;
        this.data = null;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public  void setMessage(String message) {
        this.message = message;
    }

    public ApiResponse<Page<Product>> setData(Object data) {
        this.data = data;
        return null;
    }


}
