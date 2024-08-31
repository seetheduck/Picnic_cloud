package pack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {  // Swagger

    private static final String API_NAME = "Picnic Cloud API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Picnic Cloud API 명세서";

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version(API_VERSION) //버전
                .title(API_NAME) //이름
                .description(API_DESCRIPTION); //설명
        return new OpenAPI()
                .info(info);
    }
}
