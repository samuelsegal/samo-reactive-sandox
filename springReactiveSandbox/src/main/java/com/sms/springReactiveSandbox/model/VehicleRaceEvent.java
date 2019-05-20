package com.sms.springReactiveSandbox.model;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VehicleRaceEvent {

	private Vehicle vehicle;
	private Duration raceTime;
}
