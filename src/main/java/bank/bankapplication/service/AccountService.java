package bank.bankapplication.service;

import bank.bankapplication.dto.AccountDto;
import bank.bankapplication.model.Account;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountdto);
    AccountDto getAccountById(Long id);
    AccountDto updateAccount(Long id, AccountDto accountdto);
    void deleteAccount(Long id);

    AccountDto deposite(Long id, double amount);
    AccountDto withdraw(Long id, double amount);
    AccountDto transfer(Long fromId, Long toId, double amount);
    AccountDto getBalance(Long id);
  List<  AccountDto> getAllAccounts();




}
