package com.lekhanditsove.backend.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "BOOK")
public class BookDTOXml {
    @JacksonXmlProperty(localName = "TITLE")
    private String bookTitle;
    @JacksonXmlProperty(localName = "AUTHOR")
    private String bookAuthor;
    @JacksonXmlProperty(localName = "DESCRIPTION")
    private String bookDescription;
}