package com.example.dto.request;



import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    private String categoryName;
    private Boolean status =true;
}
