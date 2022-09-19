package com.edu.ulab.app.mapper.dto;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDtoMapper {
    Book bookDtoToBook(BookDto bookDto);

    BookDto bookToBookDto(Book book);

}
