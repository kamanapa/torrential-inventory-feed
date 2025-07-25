package com.joshi.inv.feed.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class OrdersConfigServer {
  public static void main(String[] args) {
    SpringApplication.run(OrdersConfigServer.class, args);
  }
}
