package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmExistBeforePostException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Integer, Film> listFilms = new HashMap<>();

    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("не верная дата во входящих данных");
        }
        if (listFilms.containsKey(film.getId())) {
            throw new FilmExistBeforePostException("фильм уже есть в базе");
        }
        listFilms.put(film.getId(), film);
        log.info("создан новый фильм: {}", film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("не верная дата во входящих данных");
        }
        if (!listFilms.containsKey(film.getId())) {
            throw new ValidationException("нет такого фильма");
        }
        listFilms.put(film.getId(), film);
        log.info("обновлён фильм: {}", film);
        Film.idCounter++;
        return film;
    }

    @GetMapping()
    public List<Film> getAllFilms() {
        return new ArrayList<>(listFilms.values());
    }

}
