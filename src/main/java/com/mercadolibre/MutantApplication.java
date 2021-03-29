package com.mercadolibre;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MutantApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MutantApplication.class);

    public static void main(String... args) {
        SpringApplication.run(MutantApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(@Value(com.mercadolibre.common.utility.ConstantManager.ENVAPP) String env) {
        return args -> LOGGER.info(env);
    }

}
