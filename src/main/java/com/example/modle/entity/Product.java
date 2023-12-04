package com.example.modle.entity;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    private int productId;
    private String  productName;
    private  String description;
    private Double price;
    private String image;
    private Category category;
    private int stock;
    private Boolean status;
}
