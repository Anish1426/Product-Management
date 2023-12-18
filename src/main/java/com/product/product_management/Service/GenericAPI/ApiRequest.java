package com.product.product_management.Service.GenericAPI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
public class ApiRequest<T> {
    public List<T> data;

    public ApiRequest() {
        this.data = data;
    }
}
