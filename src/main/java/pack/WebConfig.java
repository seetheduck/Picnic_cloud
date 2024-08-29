package pack;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig implements WebMvcConfigurer {
	
	/*
	 * Access to fetch at 'http://localhost/fleaMarket?page=' 
	 * from origin 'http://127.0.0.1:5500' has been blocked by CORS policy: 
	 * No 'Access-Control-Allow-Origin' header is present on the requested resource.
	 * If an opaque response serves your needs,
	 * set the request's mode to 'no-cors' to fetch the resource with CORS disabled.
	 * */
	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("http://localhost:3000") // 클라이언트 도메인
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*");
	    }
}

