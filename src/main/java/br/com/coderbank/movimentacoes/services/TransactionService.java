package br.com.coderbank.movimentacoes.services;

import br.com.coderbank.movimentacoes.dtos.request.TransactionRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.TransactionResponseDTO;
import br.com.coderbank.movimentacoes.entities.Transaction;
import br.com.coderbank.movimentacoes.entities.enums.TransactionStatus;
import br.com.coderbank.movimentacoes.entities.enums.TransactionType;
import br.com.coderbank.movimentacoes.repositories.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponseDTO handleTransaction(final TransactionRequestDTO transactionRequestDTO){

        return switch (transactionRequestDTO.transactionType()){
            case SAQUE -> null;
            case DEPOSITO -> this.deposit(transactionRequestDTO);
            case TRANSFERENCIA -> null;
        };
    }

    private TransactionResponseDTO deposit(final TransactionRequestDTO transactionRequestDTO){

        var transactionEntity = new Transaction();

        BeanUtils.copyProperties(transactionRequestDTO, transactionEntity);

        transactionEntity.setTransactionType(TransactionType.DEPOSITO);
        transactionEntity.setTransactionStatus(TransactionStatus.CONCLUIDA);
        transactionEntity.setCreatedAt(String.valueOf(LocalDateTime.now()));


        transactionRepository.save(transactionEntity);

        return new TransactionResponseDTO(
                transactionEntity.getIdTransaction(),
                transactionEntity.getTransactionType(),
                transactionEntity.getAmount(),
                transactionEntity.getCreatedAt(),
                transactionEntity.getTransactionStatus()
        );

    }
}
