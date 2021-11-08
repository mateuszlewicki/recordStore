package recordstore.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    private static final String ACCOUNTS_PATH = "/accounts/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenReadAllArtists_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenReadOneArtist_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH + 1))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenReadOneArtistWithNotExistsId_thenStatusIsNotFound() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH + 4))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}