package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "name should not be empty")
    @Size(min = 2)
    private String name;

    @NotEmpty(message = "email should not be empty")
    @Size(min = 8)
    @Email
    private String email;

    @NotEmpty(message = "name should not be empty")
    private  String body;
}
