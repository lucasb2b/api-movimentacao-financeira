package br.com.coderbank.movimentacoes.repositories;

import br.com.coderbank.movimentacoes.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
