package com.sms.springReactiveSandbox.services.impl;

import java.time.Duration;
import java.util.Random;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.sms.springReactiveSandbox.events.VehicleCreatedEvent;
import com.sms.springReactiveSandbox.model.Vehicle;
import com.sms.springReactiveSandbox.model.VehicleRaceEvent;
import com.sms.springReactiveSandbox.repo.VehicleReactiveMongoRepository;
import com.sms.springReactiveSandbox.services.VehicleService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {

	private VehicleReactiveMongoRepository vehicleRepo;
	private ApplicationEventPublisher publisher;

	public VehicleServiceImpl(VehicleReactiveMongoRepository vehicleRepo, ApplicationEventPublisher publisher) {
		super();
		this.vehicleRepo = vehicleRepo;
		this.publisher = publisher;
	}

	@Override
	public Mono<Vehicle> getById(String id) {
		return vehicleRepo.findById(id);
	}

	@Override
	public Flux<Vehicle> getAll() {
		return vehicleRepo.findAll();
	}

	/**
	 * Races all the vehicles and sends display upon each finish of each vehicle as
	 * a stream until all vehicles complete race.
	 */
	@Override
	public Flux<Object> raceAllVehiclesStream() {
		Random rand = new Random();

		return vehicleRepo.findAll().flatMap(vehicle -> {
			int time = rand.nextInt(10) + 1;

			log.info("Time: {} for Vehicle: {}", time, vehicle.toString());
			Flux<Long> raceTime = Flux.interval(Duration.ofSeconds(time)).take(1);
			Flux<VehicleRaceEvent> race = Flux
					.fromStream(Stream.generate(() -> new VehicleRaceEvent(vehicle, Duration.ofSeconds(time))));
//			Flux<Vehicle> race = Flux
//					.fromStream(Stream.generate(() -> vehicle));

//			return Flux.zip(raceTime, race).map(Tuple2::getT2);
			return Flux.zip(raceTime, race);
		});
	}

	@Override
	public Mono<Vehicle> createVehicle(Vehicle vehicle) {
		return vehicleRepo.save(vehicle).doOnSuccess(v -> this.publisher.publishEvent(new VehicleCreatedEvent(v)));
	}

	@Override
	public Mono<Void> deleteAll() {
		vehicleRepo.deleteAll();
		return Mono.empty();
	}

	@Override
	public Flux<Vehicle> saveAll(Publisher<Vehicle> vehiclePublisher) {
		return vehicleRepo.saveAll(vehiclePublisher).doOnEach(v -> {
			log.debug("sdfgsdfgsdgbsdfg");
			this.publisher.publishEvent(new VehicleCreatedEvent(v.get()));
		});
	}

}
