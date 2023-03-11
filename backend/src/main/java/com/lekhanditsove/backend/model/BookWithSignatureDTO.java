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
@JacksonXmlRootElement(localName = "ENVELOPE")
public class BookWithSignatureDTO {
    @JacksonXmlProperty(localName = "BOOK")
    private BookDTOXml book;
    @JacksonXmlProperty(localName = "SIGNATURE")
    private String signature;
}

//@XmlRootElement(name = "BookEnvelope")
//public class BookWithSignatureDTO {
//    @XmlElement(name = "book")
//    private BookDTOXml book;
//    @XmlElement(name = "signature")
//    private String signature;
//}