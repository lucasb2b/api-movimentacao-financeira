package br.com.coderbank.movimentacoes.dtos.request;

import br.com.coderbank.movimentacoes.entities.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDTO(
        UUID idAccountSource,
        TransactionType transactionType,
        UUID idAccountDestination,
        String description,
        @Positive(message = "O valor deve ser positivo.")
        @DecimalMin(value = "0.01", message = "O valor dever diferente de zero.")
        BigDecimal amount

){

}
