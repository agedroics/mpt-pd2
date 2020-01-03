package ag11210.pd2.configuration;

import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JaxbAnnotationModule jaxbAnnotationModule() {
        return new JaxbAnnotationModule();
    }
}
