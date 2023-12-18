package com.product.product_management.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mongodb.lang.NonNull;
import com.product.product_management.Entity.Enum.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Document(collection = "Product Management")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EnableMongoAuditing
@Configuration
public class Product {


    public static final String SEQUENCE_NAME="sequence";

    @Id
    private int id;

    @Field("productName")
    @NonNull
    private String productName;

    @Field("Type")
    @NonNull
    private ProductType productType;

    @NonNull
    private String description;

    @NonNull
    private String unitOfMeasure;

    @NonNull
    private Integer price;

    @NonNull
    private Integer quantity;

    @NonNull
    private Integer taxPercent;

    @NonNull
    private Integer cost;

    @NonNull
    private String notes;

    @NonNull
    private String taxCode;


    @CreatedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createDate =LocalDateTime.now();

    @NonNull
    @LastModifiedDate
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updateDate;

    @NonNull
    @CreatedBy
    private String createdBy="Anish";

    @NonNull
    @LastModifiedBy
    private String updatedBy;



 }
