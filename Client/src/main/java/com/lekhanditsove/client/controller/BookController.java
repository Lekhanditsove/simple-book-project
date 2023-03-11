package com.lekhanditsove.client.controller;

import com.lekhanditsove.client.dto.BookDTOJson;
import com.lekhanditsove.client.dto.BookDTOXmlGet;
import com.lekhanditsove.client.dto.BookDTOXmlPost;
import com.lekhanditsove.client.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/book")
    public BookDTOJson createBooks(@RequestBody BookDTOJson book){
        return bookService.create(book);
    }

    @GetMapping("/book")
    public List<BookDTOJson> getBooks(){
        return bookService.findAll();
    }

    @GetMapping("/bookxml")
    public List<BookDTOXmlGet> getBooksXML(){
        return bookService.findAllXml();
    }

    @PostMapping(value = "/bookxml")
    public BookDTOXmlGet createBook(@RequestBody BookDTOXmlPost book){
        return bookService.createXml(book);
    }
    @GetMapping("/book/{id}")
    public BookDTOJson getBookById(@PathVariable("id") Integer id){
        return bookService.findById(id);
    }

    @GetMapping("/bookxml/{id}")
    public BookDTOXmlGet getBookByIdXML(@PathVariable("id") Integer id){
        return bookService.findByIdXml(id);
    }


}
