package com.ananops.provider.config;

import com.ananops.core.config.PcObjectMapper;
import com.ananops.core.config.SwaggerConfiguration;
import com.ananops.core.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



import javax.annotation.Resource;
import java.util.List;


/**
 * Created by rongshuai on 2019/11/27 13:06
 */
@Configuration
@EnableWebMvc
@Import(SwaggerConfiguration.class)
public class ImcWebMvcConfig extends WebMvcConfigurerAdapter {

    @Resource
    private TokenInterceptor vueViewInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/");
    }

    /**
     * Add interceptors.
     *
     * @param registry the registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(vueViewInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-resources/**", "*.js", "/**/*.js", "*.css", "/**/*.css", "*.html", "/**/*.html");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        PcObjectMapper.buidMvcMessageConverter(converters);
    }
}
