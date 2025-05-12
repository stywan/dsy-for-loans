package com.microservice.library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long book_id;
    private String title;
    private String isbn;
    private String publisher;
    private int publication_year;
    private String description;
    private String status;
}
