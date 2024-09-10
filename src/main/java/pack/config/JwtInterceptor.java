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

        // 모든 GET 요청을 허용
        if (request.getMethod().equalsIgnoreCase("GET")) {
            // /mypage로 시작하는 GET 요청에 대해서만 권한을 요구
            if (request.getRequestURI().startsWith("/mypage") || request.getRequestURI().startsWith("/api")) {
                // JWT 토큰을 검사하여 유효하지 않으면 401 Unauthorized
                String token = request.getHeader("Authorization");
                if (token == null || !token.startsWith("Bearer ")) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
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

                    // **유효한 토큰일 경우 userId를 request에 저장**
                    request.setAttribute("userId", userId);

                } catch (Exception e) {
                    // 토큰이 유효하지 않거나 만료된 경우
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
            }
            // GET 요청이면 여기서 종료
            return true;
        }

        // OPTIONS 요청은 인증을 요구하지 않도록 예외 처리
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true; // OPTIONS 요청은 그냥 통과시킴
        }

        // HTTP 요청에서 Authorization 헤더에서 JWT 토큰 추출
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
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

            // **유효한 토큰일 경우 userId를 request에 저장**
            request.setAttribute("userId", userId);

        } catch (Exception e) {
            // 토큰이 유효하지 않거나 만료된 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

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
