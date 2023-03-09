package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmExistBeforePostException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final Map<Integer, User> listUser = new HashMap<>();

    @PostMapping()
    public User createUser(@Valid @RequestBody  User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (listUser.containsKey(user.getId())) {
            throw new FilmExistBeforePostException("пользователь уже есть в базе");
        }

        listUser.put(user.getId(), user);
        log.info("создан новый пользователь: {}" , user);
        User.idCounter++;
        return user;
    }

    @PutMapping()
    public User updateUser(@Valid @RequestBody User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
        if (!listUser.containsKey(user.getId())){
            throw new ValidationException("нет такого пользователя");
        }
        listUser.put(user.getId(), user);
        log.info("обновлён пользователь: {}" , user);

        return user;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return new ArrayList<>(listUser.values());
    }

}
