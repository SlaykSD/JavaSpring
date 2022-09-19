package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
public class UserRequest {
    @Size( max =20,message = "limit 20 characters")
    private String fullName;

    @Size( max =20,message = "limit 20 characters")
    private String title;

    @Min(value = 0,message = "real age must be more than 0")
    private int age;
}
