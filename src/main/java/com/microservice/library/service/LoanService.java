package com.microservice.library.service;

import com.microservice.library.DTO.BookDTO;
import com.microservice.library.DTO.LoanRequestDTO;
import com.microservice.library.DTO.LoanResponseDTO;
import com.microservice.library.DTO.UserDTO;
import com.microservice.library.client.RestClientService;
import com.microservice.library.exception.ResourceNotFoundException;
import com.microservice.library.model.Loan;
import com.microservice.library.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final RestClientService restClientService;
    public LoanResponseDTO createLoan(LoanRequestDTO dto) {

        UserDTO user = restClientService.getUserById(dto.getUserId());
        if (!Boolean.TRUE.equals(user.getIs_valid())) {
            throw new IllegalStateException("User is not valid to request a loan");
        }
        BookDTO book = restClientService.getBookById(dto.getBookId());
        if (!"A".equalsIgnoreCase(book.getStatus())) {
            throw new IllegalStateException("Book is not available for loan. Current status: " + book.getStatus());
        }

        Loan loan = new Loan();
        loan.setUserId(dto.getUserId());
        loan.setBookId(dto.getBookId());
        loan.setLoanDate(dto.getLoanDate());
        loan.setDueDate(dto.getDueDate());
        loan.setStatus(Loan.LoanStatus.A);

        loan = loanRepository.save(loan);
        restClientService.updateBookStatus(dto.getBookId(), "C");

        return mapToResponse(loan, user, book);
    }

    public LoanResponseDTO getLoanById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        UserDTO user = restClientService.getUserById(loan.getUserId());
        BookDTO book = restClientService.getBookById(loan.getBookId());

        return mapToResponse(loan, user, book);
    }

    private LoanResponseDTO mapToResponse(Loan loan, UserDTO user, BookDTO book) {
        return new LoanResponseDTO(
                loan.getLoanId(),
                loan.getLoanDate(),
                loan.getDueDate(),
                loan.getReturnDate(),
                loan.getStatus().name(),
                user,
                book
        );
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public void deleteLoan(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Loan not found");
        }
        loanRepository.deleteById(id);
    }

    public LoanResponseDTO returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (!Loan.LoanStatus.A.equals(loan.getStatus())) {
            throw new IllegalStateException("Only active loans can be returned.");
        }

        loan.setStatus(Loan.LoanStatus.R);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        restClientService.updateBookStatus(loan.getBookId(), "A");

        UserDTO user = restClientService.getUserById(loan.getUserId());
        BookDTO book = restClientService.getBookById(loan.getBookId());
        return mapToResponse(loan, user, book);
    }

    public LoanResponseDTO LoanAsLost(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        if (loan.getStatus() == Loan.LoanStatus.R || loan.getStatus() == Loan.LoanStatus.L) {
            throw new IllegalStateException("Cannot mark a returned or already lost loan as lost.");
        }

        loan.setStatus(Loan.LoanStatus.L);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        restClientService.updateBookStatus(loan.getBookId(), "L");

        UserDTO user = restClientService.getUserById(loan.getUserId());
        BookDTO book = restClientService.getBookById(loan.getBookId());

        return mapToResponse(loan, user, book);
    }

    public void checkAndMarkOverdueLoans() {
        List<Loan> activeLoans = loanRepository.findAll().stream()
                .filter(loan -> loan.getStatus() == Loan.LoanStatus.A)
                .filter(loan -> loan.getDueDate().isBefore(LocalDate.now()))
                .toList();

        for (Loan loan : activeLoans) {
            loan.setStatus(Loan.LoanStatus.O);
            loanRepository.save(loan);
        }
    }
}
