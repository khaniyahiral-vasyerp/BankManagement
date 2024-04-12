package bank.bankapplication.service;

import bank.bankapplication.dto.AccountDto;
import bank.bankapplication.mapper.AccountMapper;
import bank.bankapplication.model.Account;
import bank.bankapplication.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountImpl implements AccountService {
    public AccountImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountDto accountdto) {
        Account account = AccountMapper.mapToAccount(accountdto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public AccountDto getAccountById(Long id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto updateAccount(Long id, AccountDto accountdto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        Account updatedAccount = AccountMapper.mapToAccount(accountdto);
        updatedAccount.setId(id);
        Account savedAccount = accountRepository.save(updatedAccount);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        accountRepository.deleteById(id);
    }


    @Override
    public AccountDto deposite(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount to withdraw");
        }
        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto transfer(Long fromId, Long toId, double amount) {
        Account fromAccount = accountRepository.findById(fromId).orElseThrow(() -> new RuntimeException("could not  find from account"));
        Account toAccount = accountRepository.findById(toId).orElseThrow(() -> new RuntimeException("could not  find to account"));
        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient amount to transfer");
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        Account savedFromAccount = accountRepository.save(fromAccount);
        Account savedToAccount = accountRepository.save(toAccount);
        return AccountMapper.mapToAccountDto(savedFromAccount);
    }

    @Override
    public AccountDto getBalance(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find account"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }
}
