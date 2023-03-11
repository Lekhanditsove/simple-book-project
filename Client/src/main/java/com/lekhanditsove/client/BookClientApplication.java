package com.lekhanditsove.client;

//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lekhanditsove.client.dto.*;
import com.lekhanditsove.client.service.AuthenticationService;
import com.lekhanditsove.client.service.BookService;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.StringWriter;
import java.util.List;
@SpringBootApplication
public class BookClientApplication implements ApplicationRunner {
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthenticationService authenticationService;
    public static void main(String[] args) {
        SpringApplication.run(BookClientApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        UserDTO userDTO = authenticationService.registerUser("dogani", "dogani123");
//        System.out.println(userDTO);

//        TokenDTO tokenDTO = authenticationService.authenticateUser("dogani", "dogani123");
//        System.out.println(tokenDTO);

//        BookDTOXmlGet book = bookService.findByIdWithAuthentication(11);
//        System.out.println(book.toString());
//

//        XmlMapper xmlMapper = new XmlMapper();
//        String bookInXMLTags = xmlMapper.writeValueAsString(book);
//        System.out.println(bookInXMLTags);


//        List<BookDTOXmlGet> books = bookService.findAllWithAuthentication();
//        System.out.println(books);


//       bookService = new BookService();
//        BookDTOJson book = bookService.findById(3);
//        System.out.println(book.toString());

//        List<BookDTOJson> books = bookService.findAll();
//////        System.out.println(books);

//        List<BookDTOXml> books = bookService.findAllXml();
//        System.out.println(books);

//        BookDTOXml book = bookService.findByIdXml(11);
//        System.out.println(book.toString());

//        BookDTOXmlPost toPostBook = new BookDTOXmlPost();
//        toPostBook.setAUTHOR(" Grace Ngulwa");
//        toPostBook.setTITLE("Huruma za Mungu");
//        toPostBook.setDESCRIPTION("Huruma za Mungu zatuvuta kwake daima");
//        BookDTOXmlGet postedBook = bookService.createXml(toPostBook);
//        System.out.println(postedBook.toString());

//        BookDTOXmlPost toPostBook = new BookDTOXmlPost();
//        toPostBook.setAUTHOR("Lhusajo Abel.Jasini Ngulwa");
//        toPostBook.setTITLE("Mungu ni Upendo");
//        toPostBook.setDESCRIPTION("Upendo wa Mungu ni Mkubwa Kwenye Maisha yetu");
//        BookDTOXmlGet postedBook = bookService.createWithAuthentication(toPostBook);
//        System.out.println(postedBook.toString());

        BookDTOXmlPost toPostBook = new BookDTOXmlPost();
        toPostBook.setAUTHOR(" Lekha Abel Jasini Ngulwa");
        toPostBook.setTITLE("Mungu ni Mkubwa");
        toPostBook.setDESCRIPTION("Mungu ni Mkubwa SANA.");
        BookDTOXmlGet postedBook = bookService.createWithAuthenticationAndSignature(toPostBook);
        System.out.println(postedBook.toString());
    }
}
