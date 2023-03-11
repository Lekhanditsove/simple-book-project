package com.lekhanditsove.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Book {
    private String BookId;
    private String bookAuthor;
    private String bookDescription;
}
