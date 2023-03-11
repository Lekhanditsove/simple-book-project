package com.lekhanditsove.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lekhanditsove.backend.model.Book;
import com.lekhanditsove.backend.model.BookDTOXml;
import com.lekhanditsove.backend.model.BookWithSignatureDTO;
import com.lekhanditsove.backend.model.Books;
import com.lekhanditsove.backend.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    public Book saveVerifiedBook(BookWithSignatureDTO bookWithSignatureDTO) throws Exception {

        XmlMapper xmlMapper = new XmlMapper();
        String receivedXML = xmlMapper.writeValueAsString(bookWithSignatureDTO);
        log.info("Step 01 -  Complete XML Received: " + receivedXML);

        BookDTOXml bookDTOXml = bookWithSignatureDTO.getBook();
        String signature = bookWithSignatureDTO.getSignature();

        String receivedBodyXML = xmlMapper.writeValueAsString(bookDTOXml);
        log.info("Step 02 - Body Message Received: " + receivedBodyXML);
        log.info("Step 03 - Signature Received: " + signature);

        Book newBook = new Book();

        Boolean signatureVerified = verifyReceivedMessage(receivedBodyXML, signature);

        if(signatureVerified == true) {
            newBook.setBookAuthor(bookDTOXml.getBookAuthor());
            newBook.setBookTitle(bookDTOXml.getBookTitle());
            newBook.setBookDescription(bookDTOXml.getBookDescription());
            return bookRepository.save(newBook);
        }else
            return newBook;
    }

    public Books getBookList() {
        var books = (List<Book>) bookRepository.findAll();
        var myBooks = new Books();
        myBooks.setBooks(books);
        return myBooks;
    }

    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    private PublicKey getPublicKeyFromKeyStore() throws Exception {
        Resource resource = new ClassPathResource("receiver_keystore.pfx");

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(resource.getFile()), "password".toCharArray());

        Certificate certificate = keyStore.getCertificate("receiverKeyPair");
        PublicKey publicKey = certificate.getPublicKey();

        return publicKey;
    }

    private Boolean verifyReceivedMessage(String receivedBodyXML, String receivedSignature) throws Exception {

           byte[] receivedSignatureInBytes = Base64.getDecoder().decode(receivedSignature);
           byte[] messageBytes = receivedBodyXML.getBytes();

            log.info("Step 04: " + receivedBodyXML);
            log.info("Step 05: " + receivedSignature);

            PublicKey publicKey = getPublicKeyFromKeyStore();
            log.info("Step 06 - Public Key successfully Loaded from the Keystore " );

            Signature signature = Signature.getInstance("SHA1withRSA");
            log.info("Step 07 - Signature Instance obtained " );

            signature.initVerify(publicKey);
            log.info("Step 08 - Signature Instance Initialized With Public Key" );

            signature.update(messageBytes);
             log.info("Step 09 - Signature Updated With Message" );

            return signature.verify(receivedSignatureInBytes);
    }
}
