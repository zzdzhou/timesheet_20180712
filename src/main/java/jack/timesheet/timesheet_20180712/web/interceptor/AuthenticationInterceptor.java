package jack.timesheet.timesheet_20180712.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/user/login");
            return false;
        }
        return true;
    }
}
