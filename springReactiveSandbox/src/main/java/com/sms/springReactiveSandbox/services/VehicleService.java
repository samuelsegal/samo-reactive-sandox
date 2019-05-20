package com.sms.springReactiveSandbox.services;

import com.sms.springReactiveSandbox.model.Vehicle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehicleService {

	Mono<Vehicle> getById(String id);
	Flux<Vehicle> getAll();
	Flux<Object> raceAllVehiclesStream();
}
