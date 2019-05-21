package com.sms.springReactiveSandbox.web.sse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.springReactiveSandbox.events.VehicleCreatedEvent;
import com.sms.springReactiveSandbox.events.VehicleCreatedEventPublisher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class VehicleSseController {

	private final Flux<VehicleCreatedEvent> events;
	private final ObjectMapper mapper;

	@Autowired
	public VehicleSseController(VehicleCreatedEventPublisher eventPublisher, ObjectMapper mapper) {
		super();
		this.events = Flux.create(eventPublisher).share();
		this.mapper = mapper;
	}

	@GetMapping(value = "/sse/vehicles", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> vehicles() {
		return events.map(vce -> {
			try {
				return mapper.writeValueAsString(vce);
			} catch (JsonProcessingException e) {
				log.error("Error in Sending Server Event: {}: {}", vce, e.getMessage());
				throw new RuntimeException(e);

			}
		});
	}

}
