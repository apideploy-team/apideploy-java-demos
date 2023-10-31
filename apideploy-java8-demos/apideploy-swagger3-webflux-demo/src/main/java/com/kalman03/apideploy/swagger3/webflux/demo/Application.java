package com.kalman03.apideploy.swagger3.webflux.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kalman03.apideploy.core.EnableApideploy;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author kalman03
 * @since 2023-08-26
 */
@EnableSwagger2
@EnableApideploy
@SpringBootApplication(scanBasePackages = { "springfox.petstore.webflux" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
