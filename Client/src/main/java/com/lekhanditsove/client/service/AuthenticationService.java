package com.lekhanditsove.client.service;

import com.lekhanditsove.client.dto.TokenDTO;
import com.lekhanditsove.client.dto.UserDTO;
import com.lekhanditsove.client.repository.TokenRepository;
import com.lekhanditsove.client.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {
    private final WebClient webClient = this.getWebClient();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    public UserDTO registerUser(String username, String password) throws InterruptedException {
         UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        UserDTO registeredUser = register(userDTO);
        //Thread.sleep(3000);
        return userRepository.save(registeredUser);
    }
    public TokenDTO authenticateUser(String username, String password) throws InterruptedException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        TokenDTO tokenDTO = authenticate(userDTO);
        return tokenRepository.save(tokenDTO);
    }

    private TokenDTO authenticate(UserDTO user){
        return webClient.post()
                .uri("/authenticate")
                .body(Mono.just(user), UserDTO.class)
                .retrieve()
                .bodyToMono(TokenDTO.class)
                .block();
    }

    private UserDTO register(UserDTO user){
        return webClient.post()
                .uri("/register")
                .body(Mono.just(user), UserDTO.class)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }

    private WebClient getWebClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build();
    }
    private Boolean isTokenExpired(TokenDTO tokenDTO) {
        final Date expiration = tokenDTO.getTokenExpirationDate();
        return expiration.before(new Date());
    }

}
