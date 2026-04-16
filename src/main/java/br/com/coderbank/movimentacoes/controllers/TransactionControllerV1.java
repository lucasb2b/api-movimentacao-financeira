package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.dtos.request.TransactionRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.TransactionResponseDTO;
import br.com.coderbank.movimentacoes.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transacoes")
public class TransactionControllerV1 {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO){

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.handleTransaction(transactionRequestDTO));
    }

}
