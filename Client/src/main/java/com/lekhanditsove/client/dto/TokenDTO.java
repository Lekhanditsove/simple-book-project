package com.lekhanditsove.client.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    @Id
    Integer id = 5;
    String token;
    Date tokenExpirationDate;
}
