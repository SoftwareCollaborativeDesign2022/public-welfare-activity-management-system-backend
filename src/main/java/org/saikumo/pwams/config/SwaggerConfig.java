package org.saikumo.pwams.config;

import org.saikumo.pwams.properties.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Autowired
	SwaggerProperties swaggerProperties;

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.OAS_30) // v2 不同
				.enable(swaggerProperties.getEnable())
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.saikumo.pwams.controller")) // 设置扫描路径
				.build();
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder().title(swaggerProperties.getApplicationName()+ "API文档")
				.description(swaggerProperties.getApplicationDescription())
				.version(swaggerProperties.getApplicationVersion())
				.build();
	}
}
