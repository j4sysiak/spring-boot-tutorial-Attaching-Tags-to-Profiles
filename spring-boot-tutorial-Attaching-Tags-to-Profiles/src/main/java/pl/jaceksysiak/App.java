package pl.jaceksysiak;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@EnableAsync 
@SpringBootApplication
//@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class App extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		
		String[] defs = {"/WEB-INF/tiles.xml"};
		
		tilesConfigurer.setDefinitions(defs);
		
		return tilesConfigurer;
	}
	
	@Bean
	public UrlBasedViewResolver tilesViewResolver() {
		UrlBasedViewResolver tilesViewResolver = new UrlBasedViewResolver();
		tilesViewResolver.setViewClass(TilesView.class);
		return tilesViewResolver;
	}
	
	
	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//old - depritiated in Spring-boot 2.x
//	@Bean
//	EmbeddedServletContainerCustomizer errorHandler() {
//		return new EmbeddedServletContainerCustomizer() {
//
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer container) {
//				container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
//			}
//			
//		};
//	}
	
	// 1. sposób obsługi w Spring-boot 2.x
//	@Bean
//	WebServerFactoryCustomizer<WebServerFactory> errorHandler() {
//		
//		return new WebServerFactoryCustomizer<WebServerFactory>() {
//   
//			@Override
//			public void customize(WebServerFactory container) {
//				((AbstractConfigurableWebServerFactory) container).addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
//				
//			}
//			
//		};
//	}

	
	
	// 2. sposób obsługi w Spring-boot 2.x
	@Configuration
	public class ServerConfig{
		
		@Bean
		public ConfigurableServletWebServerFactory webServerFactory(){
			
			TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
			
			factory.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
			
			return factory;
		}
	}
	
	@Bean
	PolicyFactory getUserHtmlPolicy() {
		return new HtmlPolicyBuilder()
				.allowCommonBlockElements()
				.allowCommonInlineFormattingElements()
				.toFactory();
	}
	 
} 











































