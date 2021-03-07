package com.jsimplec.places;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PlacesApplication {

  public static void main(String[] args) {
    SpringApplication.run(PlacesApplication.class, args);
  }

}
