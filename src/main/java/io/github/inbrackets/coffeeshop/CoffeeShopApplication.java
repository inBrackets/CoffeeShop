package io.github.inbrackets.coffeeshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CoffeeShopApplication {

    private static final Logger log = LoggerFactory.getLogger(CoffeeShopApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CoffeeShopApplication.class, args);
        log.info("Application started successfully!");
        log.info("Something changed");
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            String message = "The runner is up and running!";
            log.info(message);
        };
    }

}
