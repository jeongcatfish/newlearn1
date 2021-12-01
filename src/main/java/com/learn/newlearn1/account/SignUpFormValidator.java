package com.learn.newlearn1.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor // final 붙은 것 생성자 자등으로 만들어줌.
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // email, nickname 중복 검사
        SignUpForm signUpForm = (SignUpForm) target;

        if(accountRepository.existsByNickname(signUpForm.getEmail())){
            errors.rejectValue("email","invalid email!!", new Object[]{signUpForm.getEmail()}, "이미 사용 중인 이메일 입니다");
        }

        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname","invalid nickname!!", new Object[]{signUpForm.getEmail()}, "이미 사용 중인 닉네임 입니다");
        }

    }
}
