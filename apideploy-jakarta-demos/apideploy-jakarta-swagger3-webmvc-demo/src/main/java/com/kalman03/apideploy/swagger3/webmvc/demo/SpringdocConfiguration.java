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
package com.kalman03.apideploy.swagger3.webmvc.demo;

import static org.springdoc.core.Constants.ALL_PATTERN;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SpringdocConfiguration {

//	@Bean
//	@Profile("!prod")
//	public GroupedOpenApi actuatorApi(OpenApiCustomiser actuatorOpenApiCustomiser,
//			OperationCustomizer actuatorCustomizer, WebEndpointProperties endpointProperties,
//			@Value("${springdoc.version}") String appVersion) {
//		return GroupedOpenApi.builder().group("Actuator").pathsToMatch(endpointProperties.getBasePath() + ALL_PATTERN)
//				.addOpenApiCustomiser(actuatorOpenApiCustomiser)
//				.addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Actuator API").version(appVersion)))
//				.addOperationCustomizer(actuatorCustomizer).pathsToExclude("/rest/actuator/health/**")
//				.pathsToExclude("/rest/actuator/health/*").build();
//	}

	@Bean
	public GroupedOpenApi usersGroup(@Value("${springdoc.version}") String appVersion) {
		return GroupedOpenApi.builder().group("users").addOperationCustomizer((operation, handlerMethod) -> {
			operation.addSecurityItem(new SecurityRequirement().addList("basicScheme"));
			return operation;
		}).addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
				.packagesToScan("org.springdoc.demo.app2").build();
	}

}
