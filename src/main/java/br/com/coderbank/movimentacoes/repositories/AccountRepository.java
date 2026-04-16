package br.com.coderbank.movimentacoes.repositories;

import br.com.coderbank.movimentacoes.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByAccountNumber(Integer accountNumber);
}
