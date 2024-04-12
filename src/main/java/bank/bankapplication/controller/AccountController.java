package bank.bankapplication.controller;

import bank.bankapplication.dto.AccountDto;
import bank.bankapplication.service.AccountService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/accounts")
@RestController
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        AccountDto updatedAccount = accountService.updateAccount(id, accountDto);
        return ResponseEntity.ok(updatedAccount);
    }

    @PutMapping("/{id}/deposite")
    public ResponseEntity<AccountDto> depositAccount(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.deposite(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdrawAccount(@PathVariable Long id, @RequestBody Map<String, Double> request) {
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.withdraw(id, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/transfer")
    public ResponseEntity<AccountDto> transferAccount(@PathVariable Long id, @RequestBody Map<String, Double> request,@RequestParam(value="toId",required = false)Long toId) {
        Long fromId = id;
        //Long toId =request.get("toId");
        Double amount = request.get("amount");
        AccountDto accountDto = accountService.transfer(fromId, toId, request.get("amount"));
        return ResponseEntity.ok(accountDto);
    }
    @GetMapping("/{id}/balance")
    public ResponseEntity<AccountDto> getBalance(@PathVariable Long id) {
        AccountDto accountDto = accountService.getBalance(id);
        return ResponseEntity.ok(accountDto);
    }
    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
       List<AccountDto> account = accountService.getAllAccounts();
        return ResponseEntity.ok(account);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }
}
