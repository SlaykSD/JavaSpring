package com.edu.ulab.app.web.request.update;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class BookUpdateRequest {

    @Min(value = 0,message = "The ID must be greater than 0")
    private Long id;

    @Size( max =20,message = "limit 20 characters")
    private String title;

    @Size( max =20,message = "limit 20 characters")
    private String author;

    @Min(value = 0,message = "A book cannot consist of no pages")
    private long pageCount;
}
