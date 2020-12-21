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

    @Test
    public void shouldRegistrationProcess() throws Exception{
        AccountDetailsServiceImpl mockService = mock(AccountDetailsServiceImpl.class);
        Account unsaved = new Account();
        when(mockService.saveUser(unsaved)).thenReturn(true);
        RegistrationController controller = new RegistrationController(mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "denis")
                .param("password", "1234")
                .param("passwordConfirm", "1234"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        verify(mockService, atLeastOnce()).saveUser(unsaved);
    }

    @Test
    public void shouldRedirectToRegistrationWithPasswordError() throws Exception{
        AccountDetailsServiceImpl mockService = mock(AccountDetailsServiceImpl.class);
        RegistrationController controller = new RegistrationController(mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "denis")
                .param("password", "1234")
                .param("passwordConfirm", "12345"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void shouldRedirectToRegistrationWithUsernameError() throws Exception{
        AccountDetailsServiceImpl mockService = mock(AccountDetailsServiceImpl.class);
        RegistrationController controller = new RegistrationController(mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "dns")
                .param("password", "1234")
                .param("passwordConfirm", "1234"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}