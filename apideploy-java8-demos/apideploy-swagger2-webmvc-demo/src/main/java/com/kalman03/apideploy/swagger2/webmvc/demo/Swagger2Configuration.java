/*
 *
 *  Copyright 2017-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package com.kalman03.apideploy.swagger2.webmvc.demo;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toSet;

import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;

import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class Swagger2Configuration {

	@Bean
	Docket petstore2() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("petstore").enable(true)
				.useDefaultResponseMessages(false).securitySchemes(singletonList(apiKey()))
				.produces(Stream.of("application/xml", "application/json").collect(toSet())).select()
				.paths((String a) -> a.startsWith("/api")).build()
//				.host("petstore.swagger.io")
				.protocols(Stream.of("http", "https").collect(toSet()));
	}

	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}
}
