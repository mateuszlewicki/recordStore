package recordstore.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerTest {

    private static final String GENRES_PATH = "/genres/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetAllGenres_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOneGenre_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH + 4)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOneGenre_thenStatusIsNotFound() throws Exception {
        this.mockMvc.perform(get(GENRES_PATH + 1)).andExpect(status().isNotFound());
    }
}