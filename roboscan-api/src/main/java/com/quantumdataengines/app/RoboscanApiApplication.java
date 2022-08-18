package com.quantumdataengines.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RoboscanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoboscanApiApplication.class, args);
		System.out.println("roboscan api running successfully!!");
	}

}
