package com.sms.springReactiveSandbox.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.sms.springReactiveSandbox.util.HttpMethodEnum;

@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class SecurityConfig {

	@Value("${sms.origins.reactjs}")
	private String ORIGIN_REACTJS;
	
	/**
	 * Configure JWT Token Resource Server
	 * @param http
	 * @return
	 */
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		
		http
		.csrf()
			//Enable CSRF, React will see it in header and send it back
			//See https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)
			.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
		.and()
		.authorizeExchange()
			//Permit Web Sockets as currently not working with Authorization header
			//Look at http://rsocket.io 
			.pathMatchers("/ws/**").permitAll()
			.anyExchange().authenticated()
		.and()
		//Handle redirect to Okta
		.oauth2Login()
		.and()
		//configure resource server - application.yml spring.security.oauth2.resourceserver
		.oauth2ResourceServer().jwt();
		
		return http.build();
			
	}
	
	/**
	 * Configure COR's for ReactJs Client
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration config = new CorsConfiguration();	
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList(ORIGIN_REACTJS));
		config.setAllowedMethods(Collections.singletonList(HttpMethodEnum.GET.name()));
		config.setAllowedHeaders(Collections.singletonList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}