package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class Film {
    @Min(0) // при развитии приложения - информация может начать сгружаться из БД или ещё каких бэкапов. При передаче данных или в самой БД может быть ошибка и в итоге мы можем получить отрицательные ID (что при последующей загрузке в БД может дать гирлянду ошибок при назначении поля ID - первичным ключиком - такое может произойти при переходе с одного типа БД на другой (на сколько я понимаю))
    private final int id;
    @NotBlank
    private String name;
    @Length(max = 200)
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    public static int idCounter = 1;

    public Film(@NonNull String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;

        this.id = idCounter;

    }
}
