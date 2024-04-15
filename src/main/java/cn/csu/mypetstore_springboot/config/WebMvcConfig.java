package cn.csu.mypetstore_springboot.config;

import cn.csu.mypetstore_springboot.Interceptors.AdminLoginInterceptor;
import cn.csu.mypetstore_springboot.Interceptors.RequestStopInterceptor;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //图片资源映射　　　　　//其中/images是访问图片资源的前缀，比如要访问test1.png。则全路径为：http://localhost:端口号/images/test1.png
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///Users/dnegel3125/Pictures/MyPetStore/images/");//此处为设置服务端存储图片的路径（前段上传到后台的图片保存位置）
    }
}
