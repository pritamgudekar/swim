package com.ci.hightide;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@PropertySource("/application.properties")
public class HightideApplication {

    public static void main(String [] args) {
        SpringApplication.run(HightideApplication.class, args);
    }
}
