/*
 *  Copyright (c) 2020, Michael Leitz
 *  <p/>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mikeleitz.sidekick;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeleitz.sidekick.bash.domain.BashOption;
import com.mikeleitz.sidekick.bash.domain.BashValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//import com.xebia.jacksonlombok.JacksonLombokAnnotationIntrospector;

/**
 * @author leitz@mikeleitz.com
 */
@SpringBootApplication
@Slf4j
public class LicketyScriptApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(LicketyScriptApplication.class, args);
    }

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules(); //Registers all modules on classpath

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaType bashValidationList = mapper.getTypeFactory().constructCollectionType(List.class, BashValidation.class);
        JavaType bashOptionSet = mapper.getTypeFactory().constructCollectionType(HashSet.class, BashOption.class);

        return mapper;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");
            }
        };
    }

/*
    @Override
    public void run(String... args) throws Exception {
        log.info("Running command line!!");

        BashOption bashOption1 = BashOption.builder().shortName('w').longName("work").argNeeded(true)
                .validation(BashValidationEnum.NOT_NULL).validation(BashValidationEnum.TRIM_WHITESPACE).build();
        BashOption bashOption2 = BashOption.builder().shortName('x').longName("extract").argNeeded(false).build();
        BashOption bashOption3 = BashOption.HELP;
        BashOption bashOption4 = BashOption.VERBOSE;

        BashScriptConfiguration configuration = new BashScriptConfiguration();
        configuration.setBashScriptName("my-new-script");
        configuration.setScriptShell(ShellOptionEnum.BASH);
        configuration.setScriptInputs(
                Stream.of(bashOption1, bashOption2, bashOption3, bashOption4)
                        .collect(Collectors.toCollection(HashSet::new)));

        BashFile bashFile = new BashFile(configuration);
        bashFile.toFile("./");
    }*/
}
