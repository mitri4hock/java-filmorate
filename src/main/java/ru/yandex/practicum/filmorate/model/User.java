package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    @Min(0)
    private final int id;
    @NonNull
    @Email
    private String email;
    @NonNull
    @NotEmpty
    @Pattern(regexp = "\\w+", message = "логин записан не правильно")
    private String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;


}
