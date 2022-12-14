package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class BookRequest {

    @Size( max =20,message = "limit 20 characters")
    private String title;

    @Size( max =20,message = "limit 20 characters")
    private String author;

    @Min(value = 1,message = "The number of pages must be a natural number")
    private long pageCount;
}
