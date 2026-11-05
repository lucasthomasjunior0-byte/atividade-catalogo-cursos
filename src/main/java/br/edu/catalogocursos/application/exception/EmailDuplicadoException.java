package br.edu.catalogocursos.application.exception;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String message) {
        super(message);
    }
}
