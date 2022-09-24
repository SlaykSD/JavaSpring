package com.edu.ulab.app.web.request.update;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserBookUpdateRequest {
    @NotNull
    private UserUpdateRequest userRequest;
    @NotNull
    private List<BookUpdateRequest> bookRequests;
}
