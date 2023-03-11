package com.lekhanditsove.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDTOJson {
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
}
