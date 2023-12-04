package com.example.dto.request;

import com.example.dto.response.RespCategoryDto;
import com.example.modle.entity.Category;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDto
{
    private String  productName;
    private  String description;
    private Double price;
    private String image;
   private Category category;
    private int stock;
    private Boolean status;
}
