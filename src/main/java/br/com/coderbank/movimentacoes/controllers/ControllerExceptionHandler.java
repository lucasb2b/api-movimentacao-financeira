package br.com.coderbank.movimentacoes.controllers;

import br.com.coderbank.movimentacoes.exceptions.*;
import jakarta.el.MethodNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

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

    @ExceptionHandler({
            AccountNotFoundException.class,
            MethodArgumentTypeMismatchException.class,
            NoHandlerFoundException.class
    })
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail standardHandle(Exception ex){
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Saldo Insuficiente para realizar a transferência");

        problemDetail.setTitle("Conflito de regra de negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.CONFLICT);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(UnknownAccountException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail handleBusiness(final UnknownAccountException exception){
        final var exceptionMessage = exception.getMessage();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleInsufficientBalance(final InsufficientBalanceException exception){
        final var exceptionMessage = exception.getMessage();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.CONFLICT);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleInvalidEnumValue(final HttpMessageNotReadableException exception){
        final var exceptionMessage = "Valor não permitido";

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleNullValue(final IllegalArgumentException exception){
        final var exceptionMessage = "O valor não pode ser nulo";

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

    @ExceptionHandler(DuplicatedAccountException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleDuplicatedAccount(final DuplicatedAccountException exception){
        final var exceptionMessage = "A conta de origem deve ser diferente da conta de destino.";

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exceptionMessage);

        problemDetail.setTitle("Conflito de Regra de Negócio");
        problemDetail.setType(URI.create("https://api.coderbank.com.br/erros/saldo-insuficiente"));
        problemDetail.setStatus(HttpStatus.CONFLICT);
        problemDetail.setDetail(exceptionMessage);
        problemDetail.setInstance(URI.create("/v1/transacoes"));

        return problemDetail;
    }

}
