package br.com.coderbank.movimentacoes.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AccountRequestDTO(
        @NotBlank(message = "O campo id_cliente é obrigatório.")
        @Size(message = "O campo deve ter pelo menos 3 caracteres.", min = 3)
        String idClient
) {
}
