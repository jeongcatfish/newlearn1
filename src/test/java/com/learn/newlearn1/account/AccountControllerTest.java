package com.learn.newlearn1.account;

import com.learn.newlearn1.domain.Account;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    JavaMailSender javaMailSender;


    @DisplayName("sign up view test")
    @Test
    public void signForm() throws Exception {
        mockMvc.perform(get("/sign-up"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

    @DisplayName("?????? ?????? ?????? -- ???????????????")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                .param("nickname","jeongmin")
                .param("email","meail....")
                .param("password","123123123")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(unauthenticated());
    }

    @DisplayName("?????? ?????? ?????? - ????????? ??????")
    @Test
    void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "jeongmin")
                        .param("email", "jeong@gmail.com")
                        .param("password", "12345678")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated());
//                .andExpect(authenticated().withUsername("jeongmin"));

        Account account = accountRepository.findByEmail("jeong@gmail.com");
        assertNotNull(account);
        assertNotEquals(account.getPassword(),"12345678");
        assertTrue(accountRepository.existsByEmail("jeong@gmail.com"));
        assertNotNull(account.getEmailCheckToken());
    }


    @DisplayName("?????? ?????? ?????? - ?????? ??? ??????")
    @Test
    void checkEmailToken_with_wrong_input() throws Exception{
        mockMvc.perform(get("/check-email-token")
                .param("token","skdfjskdfjk")
                .param("email","abcd@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"));
    }
//    @DisplayName("?????? ?????? ?????? - ?????? ??? ??????")
//    @Test
//    void checkEmailToken_with_correct_input() throws Exception{
//
//        Account account = Account.builder()
//                        .email("jeong@gmail.com").password("123123213").nickname("JM").build();
//
//        Account newAccount = accountRepository.save(account);
//        newAccount.generateEmailCheckToken();
//        newAccount.setJoinedAt(LocalDateTime.now());
//
//        mockMvc.perform(get("/check-email-token")
//                        .param("token",newAccount.getEmailCheckToken())
//                        .param("email",newAccount.getEmail())
//                        .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeDoesNotExist("error"));
//    }

//    @DisplayName("?????? ?????? ?????? - ????????? ??????")
//    @Test
//    void checkEmailToken() throws Exception {
//        Account account = Account.builder()
//                .email("test@email.com")
//                .password("12345678")
//                .nickname("keesun")
//                .build();
//        Account newAccount = accountRepository.save(account);
//        newAccount.generateEmailCheckToken();
//
//        mockMvc.perform(get("/check-email-token")
//                        .param("token", newAccount.getEmailCheckToken())
//                        .param("email", newAccount.getEmail()))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeDoesNotExist("error"))
//                .andExpect(model().attributeExists("nickname"))
//                .andExpect(model().attributeExists("numberOfUser"))
//                .andExpect(view().name("account/checked-email"))
//                .andExpect(authenticated().withUsername("keesun"));
//    }

    @DisplayName("???????????? ?????? ?????????")
    @Test
    public void passwordHasingTest(){
//        String password = "1234";
//        String hashedPassword = "";
//
//        hashedPassword = passwordEncoder.encode(password);
//        String hp = BCrypt.hashpw(password,hashedPassword);
//
//        System.out.println(hashedPassword);
//        System.out.println(hp);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "password";
        String encodePassword = passwordEncoder.encode(rawPassword);

        System.out.println(encodePassword);
        String hashpw = BCrypt.hashpw(rawPassword,encodePassword);
        System.out.println(hashpw);
    }

}