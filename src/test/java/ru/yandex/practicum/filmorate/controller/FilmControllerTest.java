package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validTest_WhenFilmNameIsNull_ThenValidError() throws Exception{
        assertThrows(NullPointerException.class, () -> new Film(null, "any description"
                                              , LocalDate.of(2022,01,01), 10)
                    );
    }

    @Test
    void validTest_WhenFilmNameIsEmpty_ThenValidError() throws Exception {
        Film film = new Film("", "desc"
                , LocalDate.of(2022,01,01), 10);
        Film film2 = new Film("any name", "desc"
                , LocalDate.of(2022,01,01), 10);

        mockMvc.perform(post("/films/new")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(post("/films/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film2)))
                .andExpect(status().isOk());
    }

    @Test
    void validTest_WhenDescriptionLengthOver200_ThenValidError() throws Exception {
        var preStrok = new StringBuilder();
        for (int i = 0; i < 201; i++) {
            preStrok.append(i);
        }
        String overBigStrok = preStrok.toString();
        Film film = new Film("testName", overBigStrok
                , LocalDate.of(2022,01,01), 10);

        mockMvc.perform(post("/film/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validTest_WhenReleaseDateBefor1895_ThenValidError() throws Exception{
        Film film = new Film("testName", "test discription"
                , LocalDate.of(1895,12,27), 10);

        mockMvc.perform(post("/film/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validTest_WhenDurationNotPositive_ThenValidError() throws Exception{
        Film film = new Film("testName", "test discription"
                , LocalDate.of(1895,12,27), 0);

        mockMvc.perform(post("/film/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().is4xxClientError());
    }

}