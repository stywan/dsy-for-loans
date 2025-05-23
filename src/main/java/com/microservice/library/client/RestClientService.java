package com.microservice.library.client;

import com.microservice.library.DTO.BookDTO;
import com.microservice.library.DTO.UserDTO;
import com.microservice.library.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

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
    public void updateBookStatus(Long bookId, String statusCode) {
        String url = "http://localhost:8082/api/books/" + bookId + "/status";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("status", statusCode);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, Void.class);
        } catch (RestClientException e) {
            throw new IllegalStateException("Failed to update book status: " + e.getMessage());
        }
    }

    public void invalidateUser(Long userId) {
        String url = "http://localhost:8081/api/users/disable/" + userId;

        try {
            restTemplate.put(url, null);
        } catch (RestClientException e) {
            throw new IllegalStateException("Failed to disable user: " + e.getMessage());
        }
    }
}
