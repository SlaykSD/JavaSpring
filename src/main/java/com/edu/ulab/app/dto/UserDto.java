package com.edu.ulab.app.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
