package com.example.project3.Controller;

import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/get")
    public ResponseEntity getAccounts(){
        return ResponseEntity.status(200).body(accountService.getAllAccounts());
    }

    @PostMapping("/add/{customerId}")
    public ResponseEntity addAccount(@PathVariable Integer customerId, @RequestBody Account account, @AuthenticationPrincipal User user){
        accountService.addAccount(account, customerId);
        return ResponseEntity.status(201).body(new ApiResponse("Account Created"));
    }

    @PutMapping("/update/{customerId}/{id}")
    public ResponseEntity updateAccount(@PathVariable Integer customerId, @PathVariable Integer id, @RequestBody Account account, @AuthenticationPrincipal User user){
        accountService.updateAccount(customerId, id, account);
        return ResponseEntity.status(200).body(new ApiResponse("Account Updated"));
    }

    @DeleteMapping("/delete/{customerId}/{id}")
    public ResponseEntity deleteAccount(@PathVariable Integer customerId, @PathVariable Integer id, @AuthenticationPrincipal User user){
        accountService.deleteAccount(customerId, id);
        return ResponseEntity.status(200).body(new ApiResponse("Account Deleted"));
    }

    @PostMapping("/deposit/{customerId}/{id}")
    public ResponseEntity deposit(@PathVariable Integer customerId, @PathVariable Integer id, @RequestParam Double amount, @AuthenticationPrincipal User user){
        accountService.deposit(customerId, id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount Deposited"));
    }

    @PostMapping("/withdraw/{customerId}/{id}")
    public ResponseEntity withdraw(@PathVariable Integer customerId, @PathVariable Integer id, @RequestParam Double amount, @AuthenticationPrincipal User user){
        accountService.withdraw(customerId, id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount Withdrawn"));
    }

    @PostMapping("/{fromCustomerId}/{fromAccountId}/transfer/{toAccountid}")
    public ResponseEntity transfer(@PathVariable Integer fromCustomerId, @PathVariable Integer fromAccountId, @PathVariable Integer toAccountid, @RequestParam Double amount, @AuthenticationPrincipal User user){
        accountService.transferFunds(fromCustomerId, fromAccountId, toAccountid, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Amount Transferred"));
    }
}
