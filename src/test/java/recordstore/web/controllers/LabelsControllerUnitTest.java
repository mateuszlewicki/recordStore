package recordstore.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import recordstore.entity.Label;
import recordstore.service.LabelService;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LabelsControllerUnitTest {

//    private MockMvc mockMvc;
//    private LabelService mockService;
//
//    @BeforeEach
//    void setup() {
//        mockService = mock(LabelService.class);
//        LabelsController controller = new LabelsController(mockService);
//        mockMvc = standaloneSetup(controller).build();
//    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelService mockService;

    @Test
    void getLabelById() throws Exception {
        // given
        Label label = createTestLabel();
        when(mockService.getLabel(label.getId())).thenReturn(label);

        // when then
        mockMvc.perform(
                get("/labels/{id}", label.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("title").value("Label"))
                .andExpect(jsonPath("description").value("Description"))
                .andExpect(jsonPath("img").value("noImageAvailable.png"));
    }

    @Test
    void getLabelById_shouldThrowEntityNotFoundException() throws Exception {
        // given
        when(mockService.getLabel(anyLong())).thenThrow(new EntityNotFoundException("Label not found"));

        // when then
        mockMvc.perform(
                get("/labels/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException))
                .andExpect(result -> assertEquals("Label not found", Objects.requireNonNull(result.getResolvedException()).getMessage()))
                .andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage().contains("Label not found")));
    }

    private Label createTestLabel() {
        Label label = new Label();
        label.setId(1);
        label.setTitle("Label");
        label.setDescription("Description");
        label.setImg("noImageAvailable.png");
        return label;
    }
}