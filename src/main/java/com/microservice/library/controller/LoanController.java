package com.microservice.library.controller;

import com.microservice.library.DTO.LoanRequestDTO;
import com.microservice.library.DTO.LoanResponseDTO;
import com.microservice.library.model.Loan;
import com.microservice.library.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody LoanRequestDTO dto) {
        return new ResponseEntity<>(loanService.createLoan(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAll() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponseDTO> returnLoan(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.returnLoan(id));
    }

    @PutMapping("/{id}/lost")
    public ResponseEntity<LoanResponseDTO> markAsLost(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.LoanAsLost(id));
    }

    @PutMapping("/check-overdue")
    public ResponseEntity<String> checkOverdueLoans() {
        loanService.checkAndMarkOverdueLoans();
        return ResponseEntity.ok("Overdue loans updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

}
