package br.com.coderbank.movimentacoes.exceptions;

public class UnknownAccountException extends RuntimeException{
    public UnknownAccountException(String message) {
        super(message);
    }
}
