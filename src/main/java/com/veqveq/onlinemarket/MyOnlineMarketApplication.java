package com.veqveq.onlinemarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:secured.properties")
public class MyOnlineMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyOnlineMarketApplication.class, args);
    }

}
