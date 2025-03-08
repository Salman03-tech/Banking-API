package com.BankingAngular.service.inter;

import com.BankingAngular.payload.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(Long id);

    AccountDto depositAccount(Long id, Double amount);

    AccountDto withdrawAccount(Long id, Double amount);

    List<AccountDto> getAllAccount();

    void deleteAccount(Long id);

}
