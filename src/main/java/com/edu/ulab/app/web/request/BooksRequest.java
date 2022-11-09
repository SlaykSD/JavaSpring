package com.edu.ulab.app.web.request;

import lombok.Data;

import java.util.List;

@Data
public class BooksRequest {
    private  List<BookRequest> books;
}
