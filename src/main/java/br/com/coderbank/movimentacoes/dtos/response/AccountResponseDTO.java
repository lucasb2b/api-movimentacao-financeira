package br.com.coderbank.movimentacoes.dtos.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDTO(
        UUID idAccount,
        String agencyNumber,
        String accountNumber,
        String balance,
        String createdAt
) {
}
