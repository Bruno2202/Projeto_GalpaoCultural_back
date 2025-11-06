package Projeto_Padrao.Model.Exception.Handle;

import Projeto_Padrao.Model.Exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    ///TRATAMENTO DE EXCEÇÃO
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String handleException() {
        return "ERRO INTERNO!";
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDataNotFoundException(DataNotFoundException exception) {
        return exception.getMessage();
    }
}
