package com.eventbooking.bookingservice;

import com.eventbooking.common.config.FeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.eventbooking.bookingservice.client"
})
@EnableJpaAuditing
@Import(FeignClientConfig.class) // <--- ADD THIS to load the interceptor
public class BookingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingServiceApplication.class, args);
    }
}
