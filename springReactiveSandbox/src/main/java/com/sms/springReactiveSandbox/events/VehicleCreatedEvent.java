package com.sms.springReactiveSandbox.events;

import org.springframework.context.ApplicationEvent;

import com.sms.springReactiveSandbox.model.Vehicle;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("serial")
@Slf4j
public class VehicleCreatedEvent extends ApplicationEvent {


	public VehicleCreatedEvent(Vehicle source) {
		super(source);
		log.debug("Create event for new vehicle {}", source);
	}

}
