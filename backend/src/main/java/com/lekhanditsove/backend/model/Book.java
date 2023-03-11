package com.lekhanditsove.backend.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "book")
public class Book {
    @SequenceGenerator(name= "bookSequenceGenerator", allocationSize=1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookSequenceGenerator")
    @JacksonXmlProperty(localName = "id")
    private Long bookId;
    @JacksonXmlProperty(localName = "title")
    private String bookTitle;
    @JacksonXmlProperty(localName = "author")
    private String bookAuthor;
    @JacksonXmlProperty(localName = "description")
    private String bookDescription;
}
