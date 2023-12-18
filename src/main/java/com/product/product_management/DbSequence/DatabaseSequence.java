package com.product.product_management.DbSequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "db_sequence")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DatabaseSequence {
    @Id
    private String id;
    private int seq;
}
