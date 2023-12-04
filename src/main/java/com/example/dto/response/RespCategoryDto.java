package com.example.dto.response;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class RespCategoryDto {
    private int categoryId;
    private String categoryName;
    private Boolean status;
    private int productQuantity;

    @Override
    public String toString() {
        return "RespCategoryDto{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", status=" + status +
                '}';
    }
}
