package com.microservice.library.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LoanScheduler {
    private final LoanService loanService;

    public LoanScheduler(LoanService loanService) {
        this.loanService = loanService;
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void runOverdueCheck() {
        loanService.checkAndMarkOverdueLoans();
        System.out.println("[SCHEDULER] Checked for overdue loans at midnight.");
    }
}
