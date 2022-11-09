package com.edu.ulab.app.mapper.request;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.update.BookUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto bookRequestToBookDto(BookRequest bookRequest);

    BookRequest bookDtoToBookRequest(BookDto bookDto);

    BookDto bookUpdateRequestToBookDto(BookUpdateRequest bookUpdateRequest);

    BookUpdateRequest bookDtoToBookUpdateRequest(BookDto bookDto);
}
