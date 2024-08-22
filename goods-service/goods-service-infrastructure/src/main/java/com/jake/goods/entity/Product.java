package com.jake.goods.entity;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author chenjun
 * @since 2024/8/22
 */
@Document(indexName = "products")
@Data
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private double price;
    private String description;
    private String category;
    private int stock;
}
