package com.studyolle.account;

import com.studyolle.domain.Account;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    /**
     * signUpForm 흐름
     * 사용자가 /sign-up 폼 제출
     * Spring이 SignUpForm 생성 + 요청 파라미터 바인딩
     * @InitBinder("signUpForm") 실행 → SignUpFormValidator 연결
     *
     * @Valid가 붙어 있으므로
     * Bean Validation (@NotBlank, @Email, etc.)
     * Custom Validator (SignUpFormValidator)
     * 오류는 Errors 객체에 저장, 뷰로 전달
     */
    @InitBinder("signUpForm") //@Valid의 signUpForm 과 매핑된다.
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
//        model.addAttribute("signUpForm", new SignUpForm()); //"signUpForm" 없어도 이름 같으면 만들어줌
        model.addAttribute(new SignUpForm());
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid SignUpForm signUpForm, Errors errors){
        //signUpForm은 요청파라미터 -> 객체 바인딩되고,
        //@Valid로 검증, 결과는 Errors에 담긴다.
        //컨트롤러 메서드가 리턴되기전 , 스프링은 signUpForm 객체와 BindingResult를 모델에 자동으로 담아 뷰에 전달한다.
        if(errors.hasErrors()){
            return "account/sign-up";
        }

        Account account = accountService.processNewAccount(signUpForm);
        accountService.login(account);
        return "redirect:/";
    }

    @GetMapping("/check-email")
    public String checkEmail(@CurrentUser Account account, Model model){
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(@CurrentUser Account account, Model model) {
        if (!account.canSendConfirmEmail()) {
            model.addAttribute("error", "인증 이메일은 1시간에 한번만 전송할 수 있습니다.");
            model.addAttribute(account);
            return "account/check-email";
        }

        accountService.sendSignUpConfirmEmail(account);
        return "redirect:/";
    }


    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model){
        Account account = accountRepository.findByEmail(email);
        String view = "account/checked-email";

        if(account == null){
            model.addAttribute("error", "wrong.email");
            return view;
        }

        if(!account.isValidToken(token)){
            model.addAttribute("error", "wrong.token");
            return view;
        }

        account.completeSignUp();
        accountService.login(account);
        model.addAttribute("numberOfUser", accountRepository.count());
        model.addAttribute("nickname", account.getNickname());
        return view;
    }

    @GetMapping("/profile/{nickname}")
    public String viewProfile(@PathVariable String nickname, Model model, @CurrentUser Account account){
        Account byNickname = accountRepository.findByNickname(nickname);
        if(nickname == null){
            throw new IllegalArgumentException(nickname + "에 해당하는 사용자가 업습니다.");
        }

        model.addAttribute(byNickname); //account 객체로 들어간다
        model.addAttribute("isOwner", byNickname.equals(account));
        return "account/profile";
    }




}
