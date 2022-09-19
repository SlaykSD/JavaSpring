package com.edu.ulab.app.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserBookRequest {
    @NotNull
    private UserRequest userRequest;
    @NotNull
    private List<BookRequest> bookRequests;
}
