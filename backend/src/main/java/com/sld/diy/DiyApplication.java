package com.sld.diy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sld.diy.mapper")
public class DiyApplication {
  public static void main(String[] args) {
    SpringApplication.run(DiyApplication.class, args);
  }
}
