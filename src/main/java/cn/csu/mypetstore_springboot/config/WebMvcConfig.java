package cn.csu.mypetstore_springboot.config;

import cn.csu.mypetstore_springboot.Interceptors.AdminLoginInterceptor;
import cn.csu.mypetstore_springboot.Interceptors.RequestStopInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestStopInterceptor requestStopInterceptor;

    public WebMvcConfig(RequestStopInterceptor requestStopInterceptor) {
        this.requestStopInterceptor = requestStopInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login/**"); // Exclude static resources and login/register paths

        registry.addInterceptor(requestStopInterceptor)
                .excludePathPatterns("/admin/**", "/images/**", "/js/**", "/html-plugins/**", "/css/**");
    }
}
