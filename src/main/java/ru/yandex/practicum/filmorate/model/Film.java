package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class Film {
    @NotNull
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


}
