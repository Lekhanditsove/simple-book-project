package com.lekhanditsove.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lekhanditsove.backend.model.BookWithSignatureDTO;
import com.lekhanditsove.backend.model.Books;
import com.lekhanditsove.backend.service.BookService;
import com.lekhanditsove.backend.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BookController {
    @Autowired
    private BookService bookService;
    @PostMapping(value="/api/book", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public Book saveBook(@RequestBody Book book){
         return bookService.saveBook(book);
    }

    @GetMapping(value = "/api/book", produces = MediaType.APPLICATION_XML_VALUE)
    public Books getBookList(){
         return bookService.getBookList();
    }

    @PostMapping(value="/api/booka", produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.APPLICATION_XML_VALUE)
    public Book saveVerifiedBook(@RequestBody BookWithSignatureDTO book) throws Exception {
        return bookService.saveVerifiedBook(book);
    }

    @GetMapping(value= "/api/book/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public Book getBookById(@PathVariable("id") Long bookId ){
         return bookService.getBookById(bookId);

    }

}
