package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public BlogApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public BlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }
}
