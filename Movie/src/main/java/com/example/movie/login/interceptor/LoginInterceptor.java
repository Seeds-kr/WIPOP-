package com.example.movie.login.interceptor;

import com.example.movie.login.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("인터셉터가 요청을 가로챘습니다: " + requestURI); // 요청 가로챈 로그 추가

        HttpSession session = request.getSession(false); // 세션이 없을 경우 null 반환

        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청: {}", requestURI); // 미인증 로그 추가

            response.sendRedirect("/login?redirectURL=" + requestURI);
            return false;
        }

        log.info("인증된 사용자 요청: {}", requestURI); // 인증된 사용자 로그 추가
        return true; // 요청을 계속 처리하도록 true 반환
    }
}
