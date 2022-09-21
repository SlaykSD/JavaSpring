package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.BookNotFoundException;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.dto.BookDtoMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.repository.impl.BookRepository;
import com.edu.ulab.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;
    private final UserService userService;

    @Override
    public BookDto createBook(BookDto bookDto) {
        return bookDtoMapper.bookToBookDto(bookRepository.save(
                bookDtoMapper.bookDtoToBook(bookDto)));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        if(bookDto.getId()!=null){
            BookDto bookDtoUpdate = getBookById(bookDto.getId());
            if(bookDtoUpdate!=null){

                if (bookDto.getAuthor() == null) {
                    bookDto.setAuthor(bookDtoUpdate.getAuthor());
                }
                if (bookDto.getTitle() == null) {
                    bookDto.setTitle(bookDtoUpdate.getTitle());
                }
                if (bookDto.getPageCount() == 0) {
                    bookDto.setPageCount(bookDtoUpdate.getPageCount());
                }
                if (bookDto.getUserId() == null) {
                    bookDto.setUserId(bookDtoUpdate.getUserId());
                }

                return bookDtoMapper.bookToBookDto(bookRepository.save(bookDtoMapper.bookDtoToBook(bookDto)));
            }
        }
        return null;
    }

    @Override
    public BookDto getBookById(Long id) {
        if(id!=null) {
            return  bookDtoMapper.bookToBookDto(bookRepository.findById(id)
                    .orElseThrow(()->new BookNotFoundException(id)));
        }
            throw new BookNotFoundException(id);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.delete(bookDtoMapper.bookDtoToBook(getBookById(id)));
    }

    @Override
    public List<BookDto> getBooksByUserId(Long id) {
        List<Long> listBooksId = userService.getUserBookIds(id);
        List<BookDto> res= new ArrayList<>();
        listBooksId.forEach(bookId->{
            res.add(getBookById(bookId));
                });
        return  res;
//        List<Book> allBooks = (List<Book>) bookRepository.findAll();
//
//        return allBooks.stream().map(bookDtoMapper::bookToBookDto).filter(book-> book.getUserId()!=null && book.getUserId().equals(id)).toList();
    }
}
