package com.example.dto.response;
import com.example.modle.entity.Category;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RespProductDto {
    private int productId;
    private String  productName;
    private  String description;
    private Double price;
    private String image;
    private Category category;
    private String categoryName;
    private int stock;
    private Boolean status;

    @Override
    public String toString() {
        return "RespProductDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", stock=" + stock +
                ", status=" + status +
                '}';
    }
}
