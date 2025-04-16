package zoo_web.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI zooOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Zoo Web Management API")
                        .version("1.0")
                        .description("API для управления животными, вольерами и т.д."));
    }
}
