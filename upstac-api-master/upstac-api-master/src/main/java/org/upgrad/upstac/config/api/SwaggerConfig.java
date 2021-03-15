package org.upgrad.upstac.config.api;

import com.google.common.base.Predicate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.upgrad.upstac.users.roles.UserRole;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.ant;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(pathsToBeDocumented())
                .build()
                .securitySchemes(getSecuritySchemes())
                .securityContexts(getSecurityContexts());
    }


    private List<SecurityScheme> getSecuritySchemes() {
        return Collections.singletonList(new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> getSecurityContexts() {
        SecurityContext context = SecurityContext.builder()
                .securityReferences(getSecurityReferences())
                .forPaths(pathsToBeSecured())
                .build();

        return Collections.singletonList(context);
    }

    private List<SecurityReference> getSecurityReferences() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope(getScopeFor(UserRole.DOCTOR), "Doctors"),
                new AuthorizationScope(getScopeFor(UserRole.TESTER), "Testers"),
                new AuthorizationScope(getScopeFor(UserRole.GOVERNMENT_AUTHORITY), "Government Authority"),
                new AuthorizationScope(getScopeFor(UserRole.USER), "Registered users")
        };

        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
    }

     String getScopeFor(UserRole role) {
        return role.name();
    }


    private Predicate<String> pathsToBeDocumented() {
        return or(
                ant("/auth/**"),
                ant("/documents/**"),
                pathsToBeSecured()
        );
    }


    private Predicate<String> pathsToBeSecured() {
        return or(
                ant("/api/testrequests/**"),
                ant("/api/government/**"),
                ant("/api/consultations/**"),
                ant("/users/**"),
                ant("/api/labrequests/**")

        );
    }

    





    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Upstac Apis")
                .description("UPSTAC APIs for Authorities,Doctors,testers and patients")
                .contact("Upgrad")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }


}
