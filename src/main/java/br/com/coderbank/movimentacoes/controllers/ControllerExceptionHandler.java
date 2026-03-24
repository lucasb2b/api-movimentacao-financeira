package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({InvalidFieldException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail conflict(final Throwable exception){
        final var exceptionMessage = exception.getMessage();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.CONFLICT);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Erro de validação");

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);

        problemDetail.setTitle("Dados inválidos");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/validacao"));
        problemDetail.setInstance(URI.create("/v1/contas"));

        return problemDetail;
    }

}
