package br.com.coderbank.movimentacoes.exceptions;

public class InvalidMethodException extends RuntimeException{
    public InvalidMethodException(String message) {
        super(message);
    }
}
