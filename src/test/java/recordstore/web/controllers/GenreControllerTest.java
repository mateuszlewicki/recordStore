package recordstore.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    private static final String GENRES_PATH = "/genres/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetAllGenres_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetOneGenre_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH + 4))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetOneGenre_thenStatusIsNotFound() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH + 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}