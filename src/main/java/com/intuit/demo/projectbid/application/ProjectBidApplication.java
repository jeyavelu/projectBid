package com.intuit.demo.projectbid.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableEntityLinks;

@SpringBootApplication
@ComponentScan("com.intuit.demo.projectbid")
@EnableEntityLinks
public class ProjectBidApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		new ProjectBidApplication().configure(new SpringApplicationBuilder(ProjectBidApplication.class)).run(args);
	}
}
