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
public class AccountControllerTest {

    private static final String ACCOUNTS_PATH = "/accounts/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenReadAllArtists_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH)).andExpect(status().isOk());
    }

    @Test
    public void whenReadOneArtist_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH + 1)).andExpect(status().isOk());
    }

    @Test
    public void whenReadOneArtistWithNotExistsId_thenStatusIsNotFound() throws Exception {
        this.mockMvc.perform(get(ACCOUNTS_PATH + 4)).andExpect(status().isNotFound());
    }

}