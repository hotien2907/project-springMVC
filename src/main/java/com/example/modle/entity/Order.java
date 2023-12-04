package com.example.modle.entity;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Order {
    private int userId;
    private  int orderId;
    private String recipientName;
    private  double total;
    private LocalDate createDate;
    private  String phone;
    private String address;
    private int status;

    @Override
    public String toString() {
        return "Order{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", recipientName='" + recipientName + '\'' +
                ", total=" + total +
                ", createDate=" + createDate +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                '}';
    }
}
