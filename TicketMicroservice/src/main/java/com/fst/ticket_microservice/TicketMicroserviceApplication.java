package com.fst.ticket_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class TicketMicroserviceApplication {

    public static void main(final String[] args) {
        SpringApplication.run(TicketMicroserviceApplication.class, args);
    }

}
