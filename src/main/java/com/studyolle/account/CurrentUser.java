package com.studyolle.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//애노테이션 커스텀
//현재 로그인한 사용자 정보를 자동으로 받아오되, 비로그인 상태면 null로 처리
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) //매개변수에서만 커스텀 애노테이션 사용할수 있음
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {

}
