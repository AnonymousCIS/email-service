package org.anonymous.global.interceptors;

import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.UUID;

@Component
public class CommonInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String key = "userHash";

        Cookie[] cookies = request.getCookies();

        String userKey = "" + Objects.hash(key);
        String userHash = null;
        if (cookies != null){
            for (Cookie cookie: cookies){
                if (cookie.getName().equals(userKey)){
                    userHash = cookie.getValue();
                    break;
                }
            }
        }
        userHash = StringUtils.hasText(userHash) ? userHash : UUID.randomUUID().toString();

        response.setHeader("Set-Cookie", String.format("%s=%s; Path=/; HttpOnly; SameSite=None; Secure", userKey, userHash));

        return true;
    }
}
