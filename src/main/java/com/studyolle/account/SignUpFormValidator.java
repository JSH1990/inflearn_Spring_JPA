package com.studyolle.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component //컨트롤러에서 private final 사용가능하게 해줌
@RequiredArgsConstructor
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;
    private String name;

    //Validator가 어떤 타입의 객체를 검증할 수 있는지를 스프링에 알려주는 메서드
    //Validator를 실행하기 전에 "이 Validator가 이 객체를 검증할 수 있나?"를 확인하기 위해 supports()를 호출
    //"내가 어떤 타입을 검증할 수 있는지"
    @Override
    public boolean supports(Class<?> aClass) {
         return aClass.isAssignableFrom(SignUpForm.class);
    }

    //"어떻게 검증할지"
    @Override
    public void validate(Object o, Errors errors) {
        //TODO email,nickname
        SignUpForm signUpForm = (SignUpForm) o;
        if(accountRepository.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()}, "이미 사용중인 이메일입니다.");
        }

        if(accountRepository.existsByNickname(signUpForm.getNickname())){
            //new Object[]{signUpForm.getNickname()} 오류 메시지에 동적으로 값을 넣기 위한 “치환 변수(placeholder)”
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getNickname()}, "이미 사용중인 닉네임 입니다.");
        }
    }
}
