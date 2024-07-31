package com.nttdata.costoconversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.nttdata.costconversion"
})
@ComponentScan(basePackages = {"com.nttdata.costoconversion"})
@EnableJpaRepositories("com.nttdata.costoconversion.domain.repository")   
@EntityScan("com.nttdata.costoconversion.domain.model")
public class CostoconversionApplication{

	public static void main(String[] args) {
		SpringApplication.run(CostoconversionApplication.class, args);
	}

}
