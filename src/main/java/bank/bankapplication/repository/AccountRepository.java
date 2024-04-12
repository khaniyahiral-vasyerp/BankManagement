package bank.bankapplication.repository;

import bank.bankapplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository<Account,Long> {
}
