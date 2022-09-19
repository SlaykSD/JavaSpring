package com.edu.ulab.app.web.request.update;

import lombok.Data;

import java.util.List;

@Data
public class UserBookUpdateRequest {
    private UserUpdateRequest userRequest;
    private List<BookUpdateRequest> bookRequests;
}
