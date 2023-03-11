package com.lekhanditsove.backend.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JacksonXmlRootElement (localName = "books")
public class Books  {
    @JacksonXmlProperty(localName = "book")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Book> books = new ArrayList<>();
    public List<Book> getBooks(){
        return books;
    }

    public void setBooks(List<Book> books){
        this.books = books;
    }



}
