package sorokin.dev.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("sorokin.dev")
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {
}
