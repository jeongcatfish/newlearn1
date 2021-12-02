package com.learn.newlearn1.account;

import com.learn.newlearn1.ConsoleMailSender;
import com.learn.newlearn1.domain.Account;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    //PostMapping(/sign-up) 에서 signUpForm을 받을 때 실행 됨.
    @InitBinder("signUpForm")
    public void InitBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        if(errors.hasErrors()){
            System.out.println("error");
            return "account/sign-up";
        }

        accountService.processNewAccount(signUpForm);

        return "redirect:/";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){

        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account == null){
            model.addAttribute("error","wrong_email");
            return view;
        }

        if(!account.getEmailCheckToken().equals(token)){
            model.addAttribute("error","w");
            return view;
        }

        account.setEmailVerified(true);
        account.setJoinedAt(LocalDateTime.now());
        model.addAttribute("numberOfuser", accountRepository.count());
        model.addAttribute("nickname", account.getNickname());

        return view;
    }

    @GetMapping("/fag")
    public String fag(){
        return "fragments";
    }
}
