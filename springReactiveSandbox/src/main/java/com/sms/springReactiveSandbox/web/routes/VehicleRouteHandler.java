package com.sms.springReactiveSandbox.web.routes;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.services.VehicleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class VehicleRouteHandler {

	private final VehicleService vehicleService;

	public VehicleRouteHandler(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}
	
	Mono<ServerResponse> all(ServerRequest request){
		return defaultReadResponse(vehicleService.getAll());
	}

	private static Mono<ServerResponse> defaultReadResponse(Flux<Vehicle> vehicles) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(vehicles, Vehicle.class);
	}
	
}
