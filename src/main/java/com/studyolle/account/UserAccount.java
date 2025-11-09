package com.studyolle.account;

import com.studyolle.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;


//시큐리티 계정과 현재 계정의 중간을 연결해주는 어뎁터 역할을 한다.
@Getter
public class UserAccount extends User {
    private Account account;

    public UserAccount(Account account){
        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }
}
