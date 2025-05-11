package com.microservice.library.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "loans")
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "loan_date", nullable = false)
    private int loanDate;

    @Column(name = "due_date", nullable = false)
    private int dueDate;

    @Column(name = "return_date")
    private int returnDate;

    public enum LoanStatus {
        A, // Active
        R, // Returned
        O, // Overdue
        L  // Lost
    }

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private LoanStatus status = LoanStatus.A;

}
