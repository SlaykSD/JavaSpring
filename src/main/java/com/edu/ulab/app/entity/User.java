package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String fullName;
    private String title;
    private int age;
}
