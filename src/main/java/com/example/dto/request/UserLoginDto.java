package com.example.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    @Size(min = 5 ,message = "Mật khẩu có ít nhất 5 ký tự .")
    private String password;

    @NotEmpty(message = "Email không được để trống.")
    @Email(message = "Email không đúng, định dạng.")
    private String email;
}
