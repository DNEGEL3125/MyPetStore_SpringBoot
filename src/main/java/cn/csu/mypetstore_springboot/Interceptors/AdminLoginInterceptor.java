package cn.csu.mypetstore_springboot.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler != null)
//            return true;
        // Check if user is logged in
        if (request.getSession().getAttribute("admin") == null) {
            // User is not logged in, redirect to login page
            response.sendRedirect("/admin/login/form/view");
            return false; // Stop further execution
        }
        return true; // Continue execution
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Post-processing logic, executed after the handler is invoked
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Cleanup tasks after the request has been completed
    }
}

