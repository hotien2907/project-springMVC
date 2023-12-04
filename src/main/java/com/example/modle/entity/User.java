package com.example.modle.entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private int userId;
    private String userName;
    private  String fullName;
    private String email;
    private String password;
    private String phone;
    private Byte role = 0;
    private Boolean status =true;


}