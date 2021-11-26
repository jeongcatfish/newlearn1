package com.learn.newlearn1.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // email, nickname
        SignUpForm signUpForm = (SignUpForm) errors;

//        if(accountRepository.existByEmail(signUpForm.getEmail())){
//            errors.reject("email","invalid.email", new Object[]{signUpForm.getEmail(),"이미 있는 이메일 입니다다"});
//       }
    }
}
