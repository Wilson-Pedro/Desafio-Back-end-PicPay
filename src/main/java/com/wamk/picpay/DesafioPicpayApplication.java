package com.wamk.picpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.wamk.picpay")
public class DesafioPicpayApplication{

	public static void main(String[] args) {
		SpringApplication.run(DesafioPicpayApplication.class, args);
	}

}
