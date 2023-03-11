package com.lekhanditsove.client.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "BOOK")
@JacksonXmlRootElement(localName = "BOOK")
@XmlAccessorType(XmlAccessType.FIELD)
public class BookDTOXmlPost {
    @JacksonXmlProperty(localName = "TITLE")
    private String TITLE;
    @JacksonXmlProperty(localName = "AUTHOR")
    private String AUTHOR;
    @JacksonXmlProperty(localName = "DESCRIPTION")
    private String DESCRIPTION;
}







