package com.sms.springReactiveSandbox.web.config;

import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sms.springReactiveSandbox.events.VehicleCreatedEvent;
import com.sms.springReactiveSandbox.events.VehicleCreatedEventPublisher;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Configuration
public class WebSocketConfiguration {

	@Bean
	Executor executor() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	HandlerMapping handleMapping(WebSocketHandler wsh) {
		return new SimpleUrlHandlerMapping() {
			{
				setUrlMap(Collections.singletonMap("/ws/vehicles", wsh));
				setOrder(10);
			}
		};
	}

	@Bean
	WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
	@Bean
	WebSocketHandler webSocketHandler(ObjectMapper mapper, VehicleCreatedEventPublisher publisher) {
		Flux<VehicleCreatedEvent> publish = Flux.create(publisher).share();
		return session -> {
			Flux<WebSocketMessage> messageFlux = publish.map(evt -> {
				try {
					return mapper.writeValueAsString(evt.getSource());
				} catch (JsonProcessingException e) {
					throw new RuntimeException(e);
				}
			}).map(str -> {
				log.debug("sending: {}", str);
				return session.textMessage(str);
			});
			return session.send(messageFlux);
		};
	}
}
