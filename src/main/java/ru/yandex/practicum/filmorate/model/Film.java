package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class Film {
    @Min(0)
    private final int id;
    @NonNull
    @NotEmpty
    private String name;
    @NonNull
    @Length(max = 200)
    private String description;
    @NonNull
    private LocalDate releaseDate;
    @NonNull
    @Min(1)
    private int duration;
    public static int idCounter = 1;

    public Film(@NonNull String name, @NonNull String description, @NonNull LocalDate releaseDate, @NonNull int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;

        this.id = idCounter;

    }
}
