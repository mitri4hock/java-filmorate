package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User {
    @Min(0)
    private final int id;
    @NonNull
    @Email
    private String email;
    @NonNull
    @NotEmpty
    @Pattern(regexp = "\\w+", message = "логин записан не правильно")
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;
    public static int idCounter = 1;

    public User(@NonNull String email, @NonNull String login, String name, @NonNull LocalDate birthday) {
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


