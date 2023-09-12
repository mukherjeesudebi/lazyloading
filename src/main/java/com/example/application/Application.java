package com.example.application;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.Theme;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "myapp")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
@EntityScan("com.example.application.entities")
@EnableJpaRepositories(basePackages = "com.example.application.repositories")
@EnableTransactionManagement
@Push(transport = Transport.LONG_POLLING)
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

	@Autowired
	DBTools dbTools;

	@PostConstruct
	protected void onInit() {
		dbTools.clear();
		dbTools.create();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
