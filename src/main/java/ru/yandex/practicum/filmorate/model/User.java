package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User {
    @Min(0)
    private final int id;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "\\w+", message = "логин записан не правильно")
    private String login;
    private String name;
    private LocalDate birthday;
    public static int idCounter = 1;

    public User(@NonNull String email, @NonNull String login, String name,  LocalDate birthday) {
        if (name == null) {
            name = login;
        }

        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;

        this.id = idCounter;
    }
}


