package br.com.coderbank.movimentacoes.dtos.response;

import br.com.coderbank.movimentacoes.entities.enums.TransactionStatus;
import br.com.coderbank.movimentacoes.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID idTransaction,
        TransactionType transactionType,
        BigDecimal amount,
        String createdAt,
        TransactionStatus transactionStatus
) {
}
