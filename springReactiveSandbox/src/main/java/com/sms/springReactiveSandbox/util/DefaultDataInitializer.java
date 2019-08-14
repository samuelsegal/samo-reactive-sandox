package com.sms.springReactiveSandbox.util;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.repo.VehicleReactiveMongoRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author samuelsegal
 * Initialize data for default profile. Demonstrates CommandLineRunner as an initialization point.
 */
@Component
@Slf4j
@Profile("default")
public class DefaultDataInitializer {
	private final VehicleReactiveMongoRepository vehicleRepo;

	@Autowired
	public DefaultDataInitializer(VehicleReactiveMongoRepository vehicleRepo) {
		super();
		this.vehicleRepo = vehicleRepo;
	}
	@Bean
	public CommandLineRunner createVehicles() {
		return args -> {
			vehicleRepo.deleteAll().subscribe(null, null, () -> {
				Stream.of(new Vehicle(UUID.randomUUID().toString(), "Jeep", "CJ7"),
						new Vehicle(UUID.randomUUID().toString(), "MGB", "Sports car"),
						new Vehicle(UUID.randomUUID().toString(), "Volvo", "Made in Sweden, paid by Ford"),
						new Vehicle(UUID.randomUUID().toString(), "Ford", "pees on Chevy"),
						new Vehicle(UUID.randomUUID().toString(), "Chevy", "Pees on Ford"))
						.forEach(v -> vehicleRepo.save(v).subscribe(lv -> log.info("Created Vehicle: {}", lv)));
			});
		};
	}
	
}
