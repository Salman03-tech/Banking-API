package com.BankingAngular.controller;

import com.BankingAngular.payload.AccountDto;
import com.BankingAngular.payload.DepositRequest;
import com.BankingAngular.service.inter.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // http://localhost:8080/api/accounts/new-account
    @PostMapping("/new-account")
    public ResponseEntity<AccountDto> createAccount (
            @RequestBody AccountDto accountDto
        ) {
        AccountDto createdAccount = accountService.createAccount(accountDto);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<AccountDto> getAccountById(
            @PathVariable Long id
    ){
        AccountDto accountDtoById = accountService.getAccountById(id);
        return new ResponseEntity<>(accountDtoById, HttpStatus.FOUND);
    }

    @PutMapping("/id/{id}/deposit")
    public ResponseEntity<AccountDto> updateBalance(
            @PathVariable Long id,
            @RequestBody DepositRequest depositRequest
            ){
        AccountDto accountDtoBalanceUpdated = accountService.depositAccount(id, depositRequest.getAmount());
        return new ResponseEntity<>(accountDtoBalanceUpdated, HttpStatus.OK);
    }

    @PutMapping("/id/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawBalance(
            @PathVariable Long id,
            @RequestBody DepositRequest withdrawRequest
    ){
        AccountDto accountDtoWithdrawBalance = accountService.withdrawAccount(id, withdrawRequest.getAmount());
        return new ResponseEntity<>(accountDtoWithdrawBalance, HttpStatus.OK);
    }

    @GetMapping("/get-all-accounts")
    public ResponseEntity<List<AccountDto>> getAllAccount (){
        List<AccountDto> allAccount = accountService.getAllAccount();
        return new ResponseEntity<>(allAccount, HttpStatus.OK);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteAccount(
            @PathVariable Long id
    ){
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);
    }

}
