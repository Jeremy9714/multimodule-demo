package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author: zhangchy05 on 2024/5/21 10:18
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置Swagger docket实例
     *
     * @return
     */
    @Bean
    public Docket createRestApi(Environment environment) {
        Profiles profiles = Profiles.of("dev", "test");
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flag)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.es"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().version("1.0").build();
    }
}
