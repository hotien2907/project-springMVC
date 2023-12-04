package com.example.modle.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    private Cart cart ;
    private Integer id ;
    private Product product;
    private Integer quantity ;

    @Override
    public String toString() {
        return "CartItem{" +
                "cart=" + cart +
                ", id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
