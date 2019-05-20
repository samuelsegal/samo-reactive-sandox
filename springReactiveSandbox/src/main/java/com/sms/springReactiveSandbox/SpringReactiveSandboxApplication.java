package com.sms.springReactiveSandbox;

import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.repo.VehicleReactiveMongoRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringReactiveSandboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveSandboxApplication.class, args);
	}

	@Bean
	CommandLineRunner createVehicles(VehicleReactiveMongoRepository repo) {
		return args -> {
			repo.deleteAll().subscribe(null, null, () -> {
				Stream.of(new Vehicle(UUID.randomUUID().toString(), "Jeep", "CJ7"),
						new Vehicle(UUID.randomUUID().toString(), "MGB", "Sports car"),
						new Vehicle(UUID.randomUUID().toString(), "Volvo", "Made in Sweden, paid by Ford"),
						new Vehicle(UUID.randomUUID().toString(), "Ford", "pees on Chevy"),
						new Vehicle(UUID.randomUUID().toString(), "Chevy", "Pees on Ford"))
						.forEach(v -> repo.save(v).subscribe(lv -> log.info("Created Vehicle: {}", lv)));
			});
		};
	}
}
