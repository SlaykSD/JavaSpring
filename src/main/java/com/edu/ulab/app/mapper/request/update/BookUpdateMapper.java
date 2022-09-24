package com.edu.ulab.app.mapper.request.update;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.web.request.update.BookUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookUpdateMapper {
    BookDto bookUpdateRequestToBookDto(BookUpdateRequest bookUpdateRequest);
    BookUpdateRequest bookDtoToBookUpdateRequest(BookDto bookDto);
}
