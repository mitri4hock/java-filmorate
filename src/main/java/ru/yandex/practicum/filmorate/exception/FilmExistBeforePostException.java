package ru.yandex.practicum.filmorate.exception;

public class FilmExistBeforePostException extends RuntimeException{

    public FilmExistBeforePostException(String message) {
        super(message);
    }
}
