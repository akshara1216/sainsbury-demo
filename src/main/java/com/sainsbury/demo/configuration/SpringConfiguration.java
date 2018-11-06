package com.sainsbury.demo.configuration;

import com.sainsbury.demo.service.SainsburyDemoService;
import com.sainsbury.demo.service.SainsburyGroceryService;
import com.sainsbury.demo.service.impl.SainsburyDemoServiceImpl;
import com.sainsbury.demo.service.impl.SainsburyGroceryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config.properties")
public class SpringConfiguration {

    @Bean
    public SainsburyGroceryService sainsburyGroceryService(){
        return new SainsburyGroceryServiceImpl();
    }

    @Bean
    public SainsburyDemoService sainsburyDemoService(){
        return new SainsburyDemoServiceImpl();
    }
}