package com.lekhanditsove.client.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lekhanditsove.client.dto.*;
import com.lekhanditsove.client.repository.TokenRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BookService {
   private final WebClient webClient = this.getWebClient();

   @Autowired
   private TokenRepository tokenRepository;
   @Autowired
   private AuthenticationService authenticationService;
   private BookWithSignatureDTO bookWithSignature = new BookWithSignatureDTO();

    public BookDTOXmlGet findByIdWithAuthentication(Integer id) throws InterruptedException {

       TokenDTO tokenDTO = tokenRepository.findById(5).get();
       String token;
       Date tokenExpirationDate = tokenDTO.getTokenExpirationDate();

       if(tokenExpirationDate.before(new Date())){
         tokenDTO = authenticationService.authenticateUser("abel", "abel123");
       }
       token = tokenDTO.getToken();

       return webClient
               .get()
               .uri("/api/book/"+id)
               .headers(h -> h.setBearerAuth(token))
               .accept(MediaType.APPLICATION_XML)
               .retrieve()
               .bodyToMono(BookDTOXmlGet.class)
              // .timeout(Duration.ofMillis(10_000))
               .block();

   }

    public List<BookDTOXmlGet> findAllWithAuthentication() throws InterruptedException {

        TokenDTO tokenDTO = tokenRepository.findById(5).get();
        String token;
        Date tokenExpirationDate = tokenDTO.getTokenExpirationDate();

        if(tokenExpirationDate.before(new Date())){
            tokenDTO = authenticationService.authenticateUser("abel", "abel123");
        }
        token = tokenDTO.getToken();

        return webClient
                .get()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_XML)
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToFlux(BookDTOXmlGet.class)
                .timeout(Duration.ofMillis(10_000))
                .collectList()
                .block();
    }

    public BookDTOXmlGet createWithAuthentication(BookDTOXmlPost book) throws InterruptedException {

        TokenDTO tokenDTO = tokenRepository.findById(5).get();
        String token;
        Date tokenExpirationDate = tokenDTO.getTokenExpirationDate();

        if(tokenExpirationDate.before(new Date())){
            tokenDTO = authenticationService.authenticateUser("abel", "abel123");
        }

        token = tokenDTO.getToken();
        return webClient
                .post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .headers(h -> h.setBearerAuth(token))
                .body(Mono.just(book), BookDTOXmlGet.class)
                .retrieve()
                .bodyToMono(BookDTOXmlGet.class)
                .timeout(Duration.ofMillis(10_000))
                .block();
    }

    public BookDTOXmlGet createWithAuthenticationAndSignature(BookDTOXmlPost book) throws Exception {

        TokenDTO tokenDTO = tokenRepository.findById(5).get();
        String token;
        Date tokenExpirationDate = tokenDTO.getTokenExpirationDate();

        if(tokenExpirationDate.before(new Date())){
            tokenDTO = authenticationService.authenticateUser("abel", "abel123");
        }
        token = tokenDTO.getToken();

        bookWithSignature = createSignatureAndMap(book);

        return webClient
                .post()
                .uri("/api/booka")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .headers(h -> h.setBearerAuth(token))
                .body(Mono.just(bookWithSignature), BookWithSignatureDTO.class)
                .retrieve()
                .bodyToMono(BookDTOXmlGet.class)
               // .timeout(Duration.ofMillis(10_000))
                .block();
    }
    public List<BookDTOJson> findAll(){
        return webClient
                .get()
                .uri("/api/book")
                .retrieve()
                .bodyToFlux(BookDTOJson.class)
                .timeout(Duration.ofMillis(10_000))
                .collectList().block();
    }

    public BookDTOJson findById(Integer id){
        return webClient
                .get()
                .uri("/api/book/"+id)
                .retrieve()
                .bodyToMono(BookDTOJson.class)
                .timeout(Duration.ofMillis(10_000))
                .block();
    }

    public BookDTOJson create(BookDTOJson book){
        return webClient
                .post()
                .uri("/api/book")
                .body(Mono.just(book), BookDTOJson.class)
                .retrieve()
                .bodyToMono(BookDTOJson.class)
                .block();
    }

    public List<BookDTOXmlGet> findAllXml(){
        return webClient
                .get()
                .uri("/api/book")
                .accept(MediaType.APPLICATION_XML)
                .retrieve()
                .bodyToFlux(BookDTOXmlGet.class)
                .timeout(Duration.ofMillis(10_000))
                .collectList()
                .block();

    }

    public BookDTOXmlGet findByIdXml(Integer id){
        return webClient.get()
                .uri("/api/book/"+id)
                .retrieve()
                .bodyToMono(BookDTOXmlGet.class)
                .timeout(Duration.ofMillis(10_000))
                .block();
    }

    public BookDTOXmlGet createXml(BookDTOXmlPost book){
        return webClient
                .post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .body(Mono.just(book), BookDTOXmlPost.class)
                .retrieve()
                .bodyToMono(BookDTOXmlGet.class)
                .block();
    }

    public WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }
    private BookWithSignatureDTO createSignatureAndMap(BookDTOXmlPost book) throws Exception {

        XmlMapper xmlMapper = new XmlMapper();
        String messageXml = xmlMapper.writeValueAsString(book);

        String signature = getDigitalSignature(messageXml);

        BookWithSignatureDTO bookWithSignatureDTO = new BookWithSignatureDTO(book, signature);

           return bookWithSignatureDTO;
    }

    private PrivateKey getPrivateKeyFromKeyStore() throws Exception {

        Resource resource = new ClassPathResource("travfdkeystore.pfx");
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(resource.getFile()), "password".toCharArray());
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("sendKeyPair", "password".toCharArray());

        return privateKey;
    }

    private String getDigitalSignature(String message) throws Exception {
        PrivateKey privateKey = getPrivateKeyFromKeyStore();
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        byte[] messageBytes = message.getBytes();
        signature.update(messageBytes);
        byte[] digitalSignatureBytes = signature.sign();
        log.info("Digital Signature: " + Base64.getEncoder().encodeToString(digitalSignatureBytes));
        return Base64.getEncoder().encodeToString(digitalSignatureBytes);
    }

}
