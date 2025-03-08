package com.BankingAngular.service.impl;

import com.BankingAngular.entity.Account;
import com.BankingAngular.mapper.AccountMapper;
import com.BankingAngular.payload.AccountDto;
import com.BankingAngular.repository.AccountRepository;
import com.BankingAngular.service.inter.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Optional<Account> opId = accountRepository.findById(id);
        if (opId.isPresent()) {
            Account account = opId.get();
            return AccountMapper.mapToAccountDto(account);
        }
        else {
            opId.orElseThrow(() ->new RuntimeException(("Account doesn't exist")));
        }
        return null;
    }

    @Override
    public AccountDto depositAccount(Long id, Double amount) {
        Optional<Account> byId = accountRepository.findById(id);
        if (byId.isPresent()) {
            Account account = byId.get();
            account.setBalance(account.getBalance() + amount);
            Account depositUpdatedAccount = accountRepository.save(account);
            AccountDto accountDtoBalanced = AccountMapper.mapToAccountDto(depositUpdatedAccount);
            return accountDtoBalanced;
        }
        else {
           byId.orElseThrow(() -> new RuntimeException("Account doesn't exist"));
        }
        return null;
    }

    @Override
    public AccountDto withdrawAccount(Long id, Double amount) {
        Optional<Account> byId = accountRepository.findById(id);
        if (byId.isPresent()) {
            Account account = byId.get();
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                Account withdrawUpdatedAccount = accountRepository.save(account);
                AccountDto accountDtoBalanced = AccountMapper.mapToAccountDto(withdrawUpdatedAccount);
                return accountDtoBalanced;
            } else {
                throw new RuntimeException("InSufficientBalance");
            }
        }else{
            throw new RuntimeException("Account doesn't exist");
        }
    }

    @Override
    public List<AccountDto> getAllAccount() {
       return  accountRepository.findAll()
                .stream()
                .map((account)->AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Optional<Account> byId = accountRepository.findById(id);
        if (byId.isPresent()) {
            accountRepository.delete(byId.get());
        } else {
            throw new RuntimeException("Account doesn't exist");
        }
    }
}
