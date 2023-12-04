package com.example.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RespUser {
    private int userId;
    private String userName;
    private  String fullName;
    private String email;
    private String phone;
    private Byte role = 0;
    private Boolean status =true;
}
