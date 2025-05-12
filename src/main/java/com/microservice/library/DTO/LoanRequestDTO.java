package com.microservice.library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDTO {
    private Long userId;
    private Long bookId;
    private LocalDate loanDate;
    private LocalDate dueDate;
}
