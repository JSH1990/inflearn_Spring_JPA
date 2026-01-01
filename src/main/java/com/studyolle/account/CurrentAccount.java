package com.studyolle.account;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 현재 로그인한 Account 엔티티를
 * Spring Security의 AuthenticationPrincipal에서 꺼내기 위한
 * 커스텀 파라미터 어노테이션
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) //메서드의 파라미터에만 사용할 수 있다
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
// ▶ Spring Security의 Authentication.getPrincipal() 객체를 기준으로 평가
// ▶ principal 이 'anonymousUser' 이면 (로그인 안 된 상태)
//      → null 반환
// ▶ 로그인 상태라면
//      → principal 내부의 account 필드를 반환
public @interface CurrentAccount {
}
