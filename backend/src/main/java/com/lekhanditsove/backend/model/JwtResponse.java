package com.lekhanditsove.backend.model;

import java.io.Serializable;
import java.util.Date;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final Date tokenExpirationDate;

    public JwtResponse(String jwttoken, Date tokenExpirationDate) {
        this.jwttoken = jwttoken;
        this.tokenExpirationDate = tokenExpirationDate;
    }

//        public JwtResponse(String jwttoken) {
//        this.jwttoken = jwttoken;
//    }

    public String getToken() {
        return this.jwttoken;
    }

    public Date getTokenExpirationDate() {
        return this.tokenExpirationDate;
    }
}