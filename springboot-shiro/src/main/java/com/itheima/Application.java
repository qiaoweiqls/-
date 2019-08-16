package com.itheima;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot∆Ù∂Ø¿‡
 * @author ASUS-
 *
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.itheima.mapper"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
