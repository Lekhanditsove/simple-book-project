package com.lekhanditsove.client.dto;

import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ENVELOPE")
public class BookWithSignatureDTO {
    private BookDTOXmlPost BOOK;
    private String SIGNATURE;
}


















