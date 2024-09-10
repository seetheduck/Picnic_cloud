package pack.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pack.service.JwtService;


@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // GET 요청은 /fleaMarket 경로에 대해 인증을 생략
        if (request.getMethod().equalsIgnoreCase("GET") && request.getRequestURI().startsWith("/fleaMarket")) {
            return true; // 인증 생략
        }

        // GET 요청은 /message 경로에 대해 인증을 생략
        if (request.getMethod().equalsIgnoreCase("GET") && request.getRequestURI().startsWith("/message")) {
            return true; // 인증 생략
        }

        // GET 요청은 토큰 검증을 생략하고 통과
        if (request.getMethod().equalsIgnoreCase("GET")) {
            if (!request.getRequestURI().startsWith("/mypage")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        // OPTIONS 요청은 인증을 요구하지 않도록 예외 처리
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true; // OPTIONS 요청은 그냥 통과시킴
        }

        // HTTP 요청에서 Authorization 헤더에서 JWT 토큰 추출
        String token = request.getHeader("Authorization");
        // 토큰이 없거나 Bearer 토큰이 아닌 경우
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; // 요청을 막음
        }

        // Bearer 다음의 실제 토큰 추출
        token = token.replace("Bearer ", "");
        String userId = "";
        try {
            // JWT 토큰 유효성 검사
            userId = jwtService.getUserFromToken(token);
            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        } catch (Exception e) {
            // 토큰이 유효하지 않거나 만료된 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 유효한 토큰인 경우 요청을 계속 진행
        request.setAttribute("userId", userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 후처리 작업이 필요한 경우 사용
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 완료 후 처리 작업이 필요한 경우 사용
    }
}
