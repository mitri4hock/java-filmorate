package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validTest_WhenEmailIsNull_ThenValidError() {
        assertThrows(NullPointerException.class, () -> new User( null, "test login"
                , "test name", LocalDate.of(2022, 01, 01))
        );
    }

    @Test
    void validTest_WhenEmailNotFormatted_ThenValidError() throws Exception {
        User user = new User( "test@test.com", "testLogin"
                , "test name", LocalDate.of(2022, 01, 01));
        User user2 = new User( "@testtest.com", "testLogin"
                , "test name", LocalDate.of(2022, 01, 01));

        mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user2)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validTest_WhenLoginIsNull_ThenValidError() {
        assertThrows(NullPointerException.class, () -> new User( "test@test.com", null
                , "test name", LocalDate.of(2022, 01, 01))
        );
    }

    @Test
    void validTest_WhenLoginContainSpace_ThenValidError() throws Exception {
        User user = new User( "test@test.com", "test Login"
                , "test name", LocalDate.of(2022, 01, 01));

        mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void validTest_WhenBirthdayInFuture_ThenValidError() throws Exception {
        User user = new User( "test@test.com", "test_Login"
                , "test name", LocalDate.of(2032, 01, 01));

        Exception exception = assertThrows(Exception.class, () -> mockMvc.perform(post("/users/")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
        );
        assertEquals("Request processing failed; nested exception is ru.yandex.practicum.filmorate.exception.ValidationException: Дата рождения не может быть в будущем", exception.getMessage());
    }

    @Test
    void validTest_WhenNameIsEmpty_ThenNameEqualsLogin() throws Exception {
        User user = new User( "test@test.com", "test_Login"
                , null, LocalDate.of(2022, 01, 01));

        var rez = mockMvc.perform(post("/users/")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertEquals( rez.contains("\"name\":\"test_Login\""),true);

    }


}