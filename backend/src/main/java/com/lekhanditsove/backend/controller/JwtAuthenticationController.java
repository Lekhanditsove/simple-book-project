package com.lekhanditsove.backend.controller;

import java.util.Date;
import java.util.Objects;

import com.lekhanditsove.backend.model.UserDTO;
import com.lekhanditsove.backend.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lekhanditsove.backend.config.JwtTokenUtil;
import com.lekhanditsove.backend.model.JwtRequest;
import com.lekhanditsove.backend.model.JwtResponse;

@RestController
@CrossOrigin
@Slf4j
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        log.info("Step 01");
       authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        log.info("Step 02");

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        log.info("Step 03");

        final String tokenValue = jwtTokenUtil.generateToken(userDetails);
        log.info("Step 04");
       final Date tokenExpirationDate = jwtTokenUtil.getExpirationDateFromToken(tokenValue);
        log.info("Step 05");
       return ResponseEntity.ok(new JwtResponse(tokenValue, tokenExpirationDate));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            log.info("Step 02b");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            log.info("Step 02c");
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}