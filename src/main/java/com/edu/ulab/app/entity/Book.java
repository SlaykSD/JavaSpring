package com.edu.ulab.app.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Book {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;
}
