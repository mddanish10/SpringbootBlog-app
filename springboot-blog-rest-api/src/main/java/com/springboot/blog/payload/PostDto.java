package com.springboot.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.Set;

@Data
public class PostDto {

    private Long id;

    //Validations

    //Title should not be empty or null
    //title shoould have atleast 2 charcter
   @NotEmpty
    @Size(min = 2, message = "title should have atleast 2 charcter")
    private String title;

    //Description should not be empty or null
    //Description shoould have atleast 10 charcter
   @NotEmpty
    @Size(min = 10,message = "descrption should have some atleast 10 character")
    private String description;

    //Contenst should not be null or empty
      @NotEmpty
    private String content;
    private Set<CommentDto> comments;

}
