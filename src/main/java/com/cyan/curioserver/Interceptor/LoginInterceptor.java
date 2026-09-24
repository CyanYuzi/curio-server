package com.cyan.curioserver.Interceptor;

import com.cyan.curioserver.exception.NotLoggedInException;
import com.cyan.curioserver.vo.LoginUserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler){
        HttpSession session = request.getSession(false);
        if (session == null){
            throw new NotLoggedInException();
        }
        LoginUserVO loginUser = (LoginUserVO) session.getAttribute("loginUser");
        if(loginUser == null || loginUser.getId() == null){
            throw new NotLoggedInException();
        }
        request.setAttribute("currentUserId",loginUser.getId());
        return true;
    }
}

