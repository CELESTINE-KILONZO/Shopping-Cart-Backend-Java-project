package com.cellythebackenddeveloper.shopping_cart.config;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShowConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
