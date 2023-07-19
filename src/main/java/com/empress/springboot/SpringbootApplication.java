package com.empress.springboot;

import jakarta.servlet.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.faces.webapp.FacesServlet;
import java.io.IOException;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean facesServletRegistration() {
		Servlet servlet = new Servlet() {
			@Override
			public void init(ServletConfig config) throws ServletException {
			}
			@Override
			public ServletConfig getServletConfig() {
				return null;
			}
			@Override
			public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

			}
			@Override
			public String getServletInfo() {
				return null;
			}
			@Override
			public void destroy() {

			}
		};
		ServletRegistrationBean registration = new ServletRegistrationBean(servlet, "*.xhtml");
		registration.setLoadOnStartup(1);
		registration.addUrlMappings("*.jr");
		return registration;
	}

	@Bean
	public ServletContextInitializer servletContextInitializer(){
		return servletContext -> {
			servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
			servletContext.setInitParameter("primefaces.THEME", "redmond");
		};
	}

	@Bean
	public RestTemplate restTemplate() { // biblioteca para comunicação rest
		return new RestTemplate();
	}
}
