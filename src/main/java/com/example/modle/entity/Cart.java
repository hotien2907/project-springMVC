package com.example.modle.entity;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cart {
    private Integer cartId ;
    private  Integer userId ;

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                '}';
    }
}
