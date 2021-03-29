package recordstore.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.*;

import recordstore.entity.Account;
import recordstore.mapper.AccountMapper;
import recordstore.service.AccountService;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

public class RegistrationControllerTest {

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void circularViewPathIssue() throws Exception {
        AccountService mockService = mock(AccountService.class);
        RegistrationController controller = new RegistrationController(accountMapper, mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration/"))
                .andDo(print());
    }

    @Test
    public void shouldShowRegistration() throws Exception {
        AccountService mockService = mock(AccountService.class);
        RegistrationController controller = new RegistrationController(accountMapper, mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(get("/registration/")).andExpect(view().name("registration"));
    }

//    @Test
//    public void shouldRegistrationProcess() throws Exception{
//        AccountService mockService = mock(AccountService.class);
//        Account unsaved = new Account();
//        when(mockService.saveUser(unsaved)).thenReturn(true);
//        RegistrationController controller = new RegistrationController(accountMapper, mockService);
//        MockMvc mockMvc = standaloneSetup(controller).build();
//        mockMvc.perform(post("/registration/")
//                .param("username", "denis")
//                .param("password", "123456")
//                .param("passwordConfirm", "123456"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/login"));
//        verify(mockService, atLeastOnce()).saveUser(unsaved);
//    }

    @Test
    public void shouldRedirectToRegistrationWithPasswordError() throws Exception{
        AccountService mockService = mock(AccountService.class);
        RegistrationController controller = new RegistrationController(accountMapper, mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "denis")
                .param("password", "123456")
                .param("passwordConfirm", "123457")
                .param("email", "user@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void shouldRedirectToRegistrationWithEmailError() throws Exception{
        AccountService mockService = mock(AccountService.class);
        RegistrationController controller = new RegistrationController(accountMapper, mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "denis")
                .param("password", "1234567")
                .param("passwordConfirm", "123457")
                .param("email", "user@test"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    public void shouldRedirectToRegistrationWithUsernameError() throws Exception{
        AccountService mockService = mock(AccountService.class);
        RegistrationController controller = new RegistrationController(accountMapper, mockService);
        MockMvc mockMvc = standaloneSetup(controller).build();
        mockMvc.perform(post("/registration/")
                .param("username", "dns")
                .param("password", "123456")
                .param("passwordConfirm", "123456")
                .param("email", "user@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }
}