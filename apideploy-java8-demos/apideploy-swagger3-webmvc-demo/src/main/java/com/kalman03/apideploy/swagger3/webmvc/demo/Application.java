package com.kalman03.apideploy.swagger3.webmvc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kalman03.apideploy.core.EnableApideploy;

/**
 * @author kalman03
 * @since 2023-08-26
 */
@EnableApideploy
@SpringBootApplication(scanBasePackages = { "springfox.petstore","com.kalman03.apideploy.swagger3.webmvc.demo" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
