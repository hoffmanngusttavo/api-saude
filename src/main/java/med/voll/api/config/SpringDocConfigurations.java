package med.voll.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hoffmann.med API")
                        .description("API Rest da aplicação Hoffmann.med, contendo as funcionalidades de CRUD ")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("hoffmann.gusttavo@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://voll.med/api/licenca")));
    }


}
