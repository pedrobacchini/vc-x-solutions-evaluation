package com.github.pedrobacchini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VcXSolutionsEvaluationApplication {

    public static void main(String[] args) {
        SpringApplication.run(VcXSolutionsEvaluationApplication.class, args);
    }

}
