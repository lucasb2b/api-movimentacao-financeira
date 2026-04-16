package br.com.coderbank.movimentacoes.services;

import br.com.coderbank.movimentacoes.dtos.request.AccountRequestDTO;
import br.com.coderbank.movimentacoes.dtos.response.AccountResponseDTO;
import br.com.coderbank.movimentacoes.entities.Account;
import br.com.coderbank.movimentacoes.entities.enums.Status;
import br.com.coderbank.movimentacoes.exceptions.AccountNotFoundException;
import br.com.coderbank.movimentacoes.exceptions.InvalidFieldException;
import br.com.coderbank.movimentacoes.repositories.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountResponseDTO save(final AccountRequestDTO accountRequestDTO){

        var accountEntity = new Account();

        BeanUtils.copyProperties(accountRequestDTO, accountEntity);

        accountEntity.setStatus(Status.ATIVO);

        accountEntity.setAccountNumber(generateAndVerifyAccountNumber());

        accountEntity.setAgencyNumber(1);

        accountEntity.setBalance(BigDecimal.ZERO);

        accountRepository.save(accountEntity);

        String acc = String.valueOf(accountEntity.getAccountNumber());
        String accountFormatted = acc.substring(0,5) + "-" + acc.substring(5);

        return new AccountResponseDTO(
                accountEntity.getIdAccount(),
                String.format("%04d", accountEntity.getAgencyNumber()),
                accountFormatted,
                accountEntity.getBalance().setScale(2).toString(),
                accountEntity.getCreatedAt()
        );
    }


    public AccountResponseDTO getAccountById(UUID idAccount){

        Account account = accountRepository.findById(idAccount).orElseThrow(() -> new AccountNotFoundException("Conta não encontrado com o ID: " + idAccount));

        String agencyFormated = String.format("%04d", account.getAgencyNumber());

        String acc = String.valueOf(account.getAccountNumber());
        String accountFormatted = acc.substring(0, 5) + "-" + acc.substring(5);

        String balanceFormatted = account.getBalance().setScale(2).toString();

        return new AccountResponseDTO(
                account.getIdAccount(),
                agencyFormated,
                accountFormatted,
                balanceFormatted,
                account.getCreatedAt()
        );
    }

    private Integer generateAndVerifyAccountNumber(){
        int accountNumber = ThreadLocalRandom.current().nextInt(100000,1000000);

        if(accountRepository.existsByAccountNumber(accountNumber)){
            return this.generateAndVerifyAccountNumber();
        }

        return  accountNumber;
    }
}
