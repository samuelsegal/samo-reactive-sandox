package com.sms.springReactiveSandbox.services;

import org.reactivestreams.Publisher;

import com.sms.springReactiveSandbox.model.Vehicle;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VehicleService {

	Mono<Vehicle> getById(String id);
	Flux<Vehicle> getAll();
	Mono<Void> deleteAll();
	Flux<Vehicle> saveAll(Publisher<Vehicle> vehiclePublisher);
	Flux<Object> raceAllVehiclesStream();
	Mono<Vehicle> createVehicle(Vehicle vehicle);
	Publisher<Vehicle> deleteById(String id);
}
