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
public class LabelControllerTest {

    private static final String LABELS_PATH = "/labels/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetAllLabels_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(LABELS_PATH)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOneLabel_thenStatusIsOk() throws Exception {
        this.mockMvc.perform(get(LABELS_PATH + 25)).andExpect(status().isOk());
    }

    @Test
    public void whenGetOneLabel_thenStatusIsNotFound() throws Exception {
        this.mockMvc.perform(get(LABELS_PATH + 1)).andExpect(status().isNotFound());
    }
}