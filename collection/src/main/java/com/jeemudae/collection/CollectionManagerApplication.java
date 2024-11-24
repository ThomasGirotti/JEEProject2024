package com.jeemudae.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.jeemudae.collection")
public class CollectionManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionManagerApplication.class, args);
    }
}
