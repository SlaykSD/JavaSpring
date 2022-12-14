package com.edu.ulab.app.web.request.update;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class BookUpdateRequest {

    @Min(value = 1,message = "The ID must be greater than 0")
    private Long id;

    @Size( max =20,message = "limit 20 characters")
    private String title;

    @Size( max =20,message = "limit 20 characters")
    private String author;

    @Min(value = 0,message = "The number of pages must be a natural number")
    private long pageCount;
}
