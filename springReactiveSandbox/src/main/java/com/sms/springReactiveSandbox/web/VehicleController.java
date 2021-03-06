package com.sms.springReactiveSandbox.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.services.VehicleService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/vehicles/api", produces=MediaType.APPLICATION_JSON_VALUE)
public class VehicleController {

	private VehicleService vehicleService;

	@Autowired
	public VehicleController(VehicleService vehicleService) {
		super();
		this.vehicleService = vehicleService;
	}

	@GetMapping("/{id}")
	public Mono<Vehicle> getById(@PathVariable String id) {
		return vehicleService.getById(id);
	}

	@GetMapping("/all")
	public Flux<Vehicle> getAll() {
		return vehicleService.getAll();
	}

	@GetMapping(value = "/raceAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> raceAllVehicles() {
		return vehicleService.raceAllVehiclesStream();
	}

	@PostMapping("/create")
	public Mono<Vehicle> create(@RequestBody Vehicle vehicle) {
		return vehicleService.createVehicle(vehicle);
	}
}
