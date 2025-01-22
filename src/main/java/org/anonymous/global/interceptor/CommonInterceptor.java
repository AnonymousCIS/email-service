package org.anonymous.global.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.UUID;

@Component
public class CommonInterceptor implements HandlerInterceptor { // 처음 들어오면 무조건 부여하고 시작하겠다. 언제 사용하느냐 비회원이 뭔갈 할 때.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = "userHash"; // userHash가 키값

        Cookie[] cookies = request.getCookies(); // 요청된 쿠키정보 확인

        /* 사용자 구분 목적의 hash 생성 S */
        String userKey= "" + Objects.hash(key); // 로컬구분 개개인의 로컬구분 왜? 로그인 안했기 때문에 구분 불가해서 로컬로 구분함.
        String userHash = null; // userHash의 기본값은 null
        if (cookies != null) { // 쿠기가 null이 아니면
            for (Cookie cookie : cookies) { // 순회하며 코드 확인
                if (cookie.getName().equals(userKey)) { // 만약 쿠키의 이름이 userKey와 같으면
                    userHash = cookie.getValue(); // 쿠키값을 userHash에 저장
                    break; // 멈춤
                }
            }
        }

        userHash = StringUtils.hasText(userHash) ? userHash : UUID.randomUUID().toString(); // userHash가 비어있지 않으면 userHash 그대로 반환 비어있으면 랜덤으러 생성

        response.setHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=None", userKey, userHash)); // 헤더 셋해줌
        /* 사용자 구분 목적의 hash 생성 E */

        return true;
    }
}
