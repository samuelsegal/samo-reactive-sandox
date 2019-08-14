package com.sms.springReactiveSandbox.util;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.services.VehicleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author samuelsegal Initialize saple data for the dev environment
 *         Demonstrates use of ApplicationListener and ApplicationReady Event.
 * @see org.springframework.boot.context.event.*mfor other Spring Application
 *      Events
 */
@Slf4j
@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private VehicleService vehicleService;

	@Autowired
	public DevDataInitializer(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Flux<Vehicle> vehicles = Flux.just(new Vehicle(UUID.randomUUID().toString(), "Jeep", "CJ7"),
				new Vehicle(UUID.randomUUID().toString(), "MGB", "Sports car"),
				new Vehicle(UUID.randomUUID().toString(), "Volvo", "Made in Sweden, paid by Ford"),
				new Vehicle(UUID.randomUUID().toString(), "Ford", "pees on Chevy"),
				new Vehicle(UUID.randomUUID().toString(), "Chevy", "Pees on Ford"));

		vehicleService.deleteAll().thenMany(vehicleService.saveAll(vehicles))
				.subscribe(vehicle -> log.debug("Created Vehicle: {}", vehicle));
	}

}
