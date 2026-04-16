package br.com.coderbank.movimentacoes.exceptions;

public class DuplicatedAccountException extends RuntimeException {
    public DuplicatedAccountException(String message) {
        super(message);
    }
}
