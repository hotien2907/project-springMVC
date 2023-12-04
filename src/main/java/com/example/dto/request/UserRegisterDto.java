package com.example.dto.request;
import lombok.*;
import org.springframework.validation.Errors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @NotEmpty(message = "Tên không được để trống !.")
    private String userName;
    @NotEmpty(message = "Họ Và tên không được để trống !.")
    private  String fullName;
    @NotEmpty(message = "Email không được để trống !.")
    @Email(message = "Email không đúng định dạng !. ")
    private String email;
    @Size(min = 5,max = 15,message = "Mật khẩu có ít nhất 5 ký tự.")
    private String password;

    private String phone;
    private Byte role = 0;


    public void CheckValidate(Errors errors, List<String> duplicateEmails) {
        if (this.phone == null || this.phone.trim().isEmpty()) {
            errors.rejectValue("phone", "phone.empty");
        } else if (!phone.matches("(\\+84|0)[0-9]{9,10}")) {
            errors.rejectValue("phone", "phone.pattern");
        }
        for (String email : duplicateEmails) {
            if (email.equals(this.email)) {
                errors.rejectValue("email", "email.duplicate");
            }
        }
    }



}








