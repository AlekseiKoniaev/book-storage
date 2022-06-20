package ru.koniaev.bookstorage.exception;

public class WrongInputParametersException extends RuntimeException {
    public WrongInputParametersException(String message) {
        super(message);
    }
}
