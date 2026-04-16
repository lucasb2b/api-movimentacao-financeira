package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.dtos.request.AccountRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.AccountResponseDTO;
import br.com.coderbank.movimentacoes.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/contas")
public class AccountControllerV1 {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponseDTO> save(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountService.save(accountRequestDTO));
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getAccountById(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }

}
