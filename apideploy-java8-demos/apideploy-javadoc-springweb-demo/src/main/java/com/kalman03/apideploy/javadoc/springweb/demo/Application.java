package com.kalman03.apideploy.javadoc.springweb.demo;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author kalman03
 * @since 2023-08-26
 */
@SpringBootApplication(scanBasePackages = { "springfox.petstore","com.kalman03.apideploy.javadoc.springweb.demo" })
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
//		ApiConfig apiConfig = new ApiConfig();
//		apiConfig.setServerUrl("");
//		apiConfig.setServerEnv("");
//		apiConfig.setParamsDataToTree(true);
//		IDocBuildTemplate<ApiDoc> docBuildTemplate = BuildTemplateFactory
//				.getDocBuildTemplate(FrameworkEnum.SPRING.getFramework());
//		JavaProjectBuilder javaProjectBuilder = JavaProjectBuilderHelper.create();
//		ProjectDocConfigBuilder configBuilder = new ProjectDocConfigBuilder(apiConfig, javaProjectBuilder);
//		List<ApiDoc> apiDocList = docBuildTemplate.getApiData(configBuilder);
//		
//		apiDocList.forEach(item->{
//			item.getTagRefs().clear();
//			item.getList().forEach(child->{
//				child.getTagRefs().clear();
//				child.setClazzDoc(null);
//			});
//		});
//		try {
//			System.out.println(build(JsonInclude.Include.NON_NULL).writeValueAsString(apiDocList));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static ObjectMapper build(JsonInclude.Include... includes) {
		ObjectMapper objectMapper = JsonMapper.builder().build()
				// ignore bean not field
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				// int or double can be null
				.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
				// enum using toString
				.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true).setLocale(Locale.CHINA)
				.registerModule(new JavaTimeModule()).setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		for (JsonInclude.Include include : includes) {
			objectMapper.setSerializationInclusion(include);
		}
		return objectMapper;
	}
}
