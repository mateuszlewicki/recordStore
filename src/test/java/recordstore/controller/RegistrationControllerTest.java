package recordstore.controller;

import org.junit.Test;
import org.springframework.test.web.servlet.*;

import recordstore.entity.Account;
import recordstore.service.AccountDetailsServiceImpl;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class RegistrationControllerTest {

    @Test
    public void circularViewPathIssue() throws Exception {
        AccountDetailsServiceImpl mockService = mock(AccountDetailsServiceImpl.class);
        RegistrationController controller = new RegistrationController(mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration/"))
                .andDo(print());
    }

    @Test
    public void shouldShowRegistration() throws Exception {
        AccountDetailsServiceImpl mockService = mock(AccountDetailsServiceImpl.class);
        RegistrationController controller = new RegistrationController(mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration/")).andExpect(view().name("registration"));
    }
}