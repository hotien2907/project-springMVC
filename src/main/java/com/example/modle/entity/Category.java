package com.example.modle.entity;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    private int categoryId;
    private String categoryName;
    private Boolean status;

}
