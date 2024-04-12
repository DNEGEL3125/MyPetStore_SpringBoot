package cn.csu.mypetstore_springboot.Interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestStopInterceptor implements HandlerInterceptor {

    private boolean stopRequests = false;

    public void stopRequests() {
        stopRequests = true;
    }

    public void startRequests() {
        stopRequests = false;
    }

    public boolean isStopped() {
        return stopRequests;
    }


    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (stopRequests) {
            // Stop processing requests
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return false;
        }
        return true;
    }
}
