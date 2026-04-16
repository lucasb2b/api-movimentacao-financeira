package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.dtos.request.AccountRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.AccountResponseDTO;
import br.com.coderbank.movimentacoes.dtos.response.PagedResponse;
import br.com.coderbank.movimentacoes.dtos.response.TransactionResponseDTO;
import br.com.coderbank.movimentacoes.services.AccountService;
import br.com.coderbank.movimentacoes.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/contas")
public class AccountControllerV1 {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> save(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.save(accountRequestDTO));
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getAccountById(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

    @GetMapping("/{idAccount}/extrato")
    public PagedResponse<TransactionResponseDTO> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable String idAccount
    ){
        var pageable = PageRequest.of(page, size);

        var pageTransactions = transactionService.getTransactions(idAccount, pageable);

        return new PagedResponse<>(pageTransactions);
    }

}
