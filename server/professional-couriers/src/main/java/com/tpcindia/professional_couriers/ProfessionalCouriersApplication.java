package com.tpcindia.professional_couriers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProfessionalCouriersApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProfessionalCouriersApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ProfessionalCouriersApplication.class, args);
	}

}
