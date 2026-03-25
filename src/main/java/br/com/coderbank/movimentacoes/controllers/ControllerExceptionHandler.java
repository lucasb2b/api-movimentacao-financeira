package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.HashMap;

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
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleValidationException(final MethodArgumentNotValidException exception){

        final var validationErrors = buildHashMapValidationsErrors(exception).toString();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, validationErrors);
        problemDetail.setTitle("Data sent in the request is invalid");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));

        return problemDetail;
    }

    private static HashMap<Object, Object> buildHashMapValidationsErrors(MethodArgumentNotValidException exception){
        final var errors = new HashMap<>();

        exception.getBindingResult()
                .getAllErrors()
                .forEach((error) -> {

                    var fieldName = ((FieldError) error).getField();

                    var errorMessage = error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

}
