package com.studyolle.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
애노테이션 커스텀
현재 로그인한 사용자 정보를 자동으로 받아오되, 비로그인 상태면 null로 처리

@CurrentUser 가 “현재 유저” 를 아는 이유는:

Spring Security가 로그인 시 Authentication.principal 에
UserAccount(account) 를 저장하기 때문. 이 클래스는 Account의 대리자 역할을 하므로, 관련 정보를 가져올수있음.
expression는 커스텀표현식이다. 로그인한 정보가 맞으면 현재 account를 반환한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) //매개변수에서만 커스텀 애노테이션 사용할수 있음
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {

}
