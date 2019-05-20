package com.sms.springReactiveSandbox.services.impl;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.model.VehicleRaceEvent;
import com.sms.springReactiveSandbox.repo.VehicleReactiveMongoRepository;
import com.sms.springReactiveSandbox.services.VehicleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

	private VehicleReactiveMongoRepository vehicleRepo;
	
	@Autowired
	public VehicleServiceImpl(VehicleReactiveMongoRepository vehicleRepo) {
		super();
		this.vehicleRepo = vehicleRepo;
	}

	@Override
	public Mono<Vehicle> getById(String id) {
		return vehicleRepo.findById(id);
	}

	@Override
	public Flux<Vehicle> getAll() {
		return vehicleRepo.findAll();
	}

	@Override
	public Flux<Object> raceAllVehiclesStream() {
		Random rand = new Random();

		return vehicleRepo.findAll().flatMap(vehicle -> {
			int time = rand.nextInt(10) + 1;
			
			log.info("Time: {} for Vehicle: {}", time, vehicle.toString());
			Flux<Long> raceTime = Flux.interval(Duration.ofSeconds(time)).take(1);
			Flux<VehicleRaceEvent> race = Flux.fromStream(
					Stream.generate(()->new VehicleRaceEvent(vehicle, Duration.ofSeconds(time))));
			return Flux.zip(raceTime, race).map(Tuple2::getT2);
		});
	}

}
