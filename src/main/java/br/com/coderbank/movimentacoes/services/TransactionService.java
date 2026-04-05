package br.com.coderbank.movimentacoes.services;

import br.com.coderbank.movimentacoes.dtos.request.TransactionRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.TransactionResponseDTO;
import br.com.coderbank.movimentacoes.entities.Transaction;
import br.com.coderbank.movimentacoes.entities.enums.TransactionStatus;
import br.com.coderbank.movimentacoes.entities.enums.TransactionType;
import br.com.coderbank.movimentacoes.exceptions.DuplicatedAccountException;
import br.com.coderbank.movimentacoes.exceptions.InsufficientBalanceException;
import br.com.coderbank.movimentacoes.repositories.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Transactional(noRollbackFor = {
            InsufficientBalanceException.class,
            DuplicatedAccountException.class
    })
    public TransactionResponseDTO handleTransaction(final TransactionRequestDTO transactionRequestDTO){

        return switch (transactionRequestDTO.transactionType()){
            case SAQUE -> this.withdraw(transactionRequestDTO);
            case DEPOSITO -> this.deposit(transactionRequestDTO);
            case TRANSFERENCIA -> this.transfer(transactionRequestDTO);
        };
    }

    private TransactionResponseDTO deposit(final TransactionRequestDTO transactionRequestDTO){

        accountService.deposit(transactionRequestDTO.idAccountDestination(), transactionRequestDTO.amount());

        var transactionEntity = new Transaction();

        BeanUtils.copyProperties(transactionRequestDTO, transactionEntity);

        transactionEntity.setIdAccountDestination(String.valueOf(transactionRequestDTO.idAccountDestination()));
        transactionEntity.setIdAccountSource(null);
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

    private TransactionResponseDTO withdraw(final TransactionRequestDTO transactionRequestDTO){

        var transactionEntity = new Transaction();
        BeanUtils.copyProperties(transactionRequestDTO, transactionEntity);
        transactionEntity.setTransactionType(TransactionType.SAQUE);
        transactionEntity.setCreatedAt(String.valueOf(LocalDateTime.now()));
        transactionEntity.setIdAccountDestination(null);
        transactionEntity.setIdAccountSource(String.valueOf(transactionRequestDTO.idAccountSource()));

        try{
            accountService.withdraw(transactionRequestDTO.idAccountSource(), transactionRequestDTO.amount());
            transactionEntity.setTransactionStatus(TransactionStatus.CONCLUIDA);
        }catch (InsufficientBalanceException e){
            transactionEntity.setTransactionStatus(TransactionStatus.FALHA);
            transactionRepository.save(transactionEntity);

            throw e;
        }

        transactionRepository.save(transactionEntity);

        return new TransactionResponseDTO(
                transactionEntity.getIdTransaction(),
                transactionEntity.getTransactionType(),
                transactionEntity.getAmount(),
                transactionEntity.getCreatedAt(),
                transactionEntity.getTransactionStatus()
        );

    }

    private TransactionResponseDTO transfer(final TransactionRequestDTO transactionRequestDTO){

        accountService.transfer(transactionRequestDTO.idAccountSource(), transactionRequestDTO.idAccountDestination(), transactionRequestDTO.amount());

        var transactionEntity = new Transaction();

        BeanUtils.copyProperties(transactionRequestDTO, transactionEntity);

        transactionEntity.setTransactionType(TransactionType.TRANSFERENCIA);
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
