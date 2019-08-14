package com.sms.springReactiveSandbox.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sms.springReactiveSandbox.model.Vehicle;

public interface VehicleReactiveMongoRepository extends ReactiveMongoRepository<Vehicle, String> {

}
