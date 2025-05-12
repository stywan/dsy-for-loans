package com.microservice.library.client;

import com.microservice.library.DTO.BookDTO;
import com.microservice.library.DTO.UserDTO;
import com.microservice.library.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestClientService {
    private final RestTemplate restTemplate;

    public UserDTO getUserById(Long userId) {
        try {
            return restTemplate.getForObject("http://localhost:8081/api/users/" + userId, UserDTO.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

    public BookDTO getBookById(Long bookId) {
        try {
            return restTemplate.getForObject("http://localhost:8082/api/books/" + bookId, BookDTO.class);
        } catch (RestClientException e) {
            throw new ResourceNotFoundException("Book not found");
        }
    }
}
