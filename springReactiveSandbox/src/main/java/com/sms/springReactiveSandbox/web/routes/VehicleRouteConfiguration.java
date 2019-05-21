package com.sms.springReactiveSandbox.web.routes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sms.springReactiveSandbox.web.predicates.CaseInsensitiveRequestPredicate;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class VehicleRouteConfiguration {
	
	@Bean
	RouterFunction<ServerResponse> routes(VehicleRouteHandler handler){
		return route(i(GET("/vehicles")), handler::all);
			//.andRoute(i(GET("/vehicles/{id}")), handler::getById);
	}
	
	private static RequestPredicate i(RequestPredicate target) {
		return new CaseInsensitiveRequestPredicate(target);
	}
}
