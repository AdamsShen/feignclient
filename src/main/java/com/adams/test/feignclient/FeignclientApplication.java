package com.adams.test.feignclient;

import com.adams.test.feignclient.config.FeignErrorDecoderConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.adams.test.feignclient"}, defaultConfiguration = FeignErrorDecoderConfig.class)
@EnableDiscoveryClient
@EnableCircuitBreaker
public class FeignclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignclientApplication.class, args);
    }

}
