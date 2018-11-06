package com.sainsbury.demo;

import com.sainsbury.demo.configuration.SpringConfiguration;
import com.sainsbury.demo.service.SainsburyDemoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        SainsburyDemoService sainsburyDemoService = context.getBean("sainsburyDemoService", SainsburyDemoService.class);

        try {
            String response = sainsburyDemoService.scrapeGroceryProducts();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
