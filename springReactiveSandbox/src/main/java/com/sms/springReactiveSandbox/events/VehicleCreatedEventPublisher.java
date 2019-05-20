package com.sms.springReactiveSandbox.events;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;

@Slf4j
@Component
public class VehicleCreatedEventPublisher
		implements ApplicationListener<VehicleCreatedEvent>, Consumer<FluxSink<VehicleCreatedEvent>> {

	private final Executor executor;
	private final BlockingQueue<VehicleCreatedEvent> queue = new LinkedBlockingQueue<>();
	
	@Autowired
	public VehicleCreatedEventPublisher(Executor executor) {
		super();
		this.executor = executor;
	}

	@Override
	public void accept(FluxSink<VehicleCreatedEvent> sink) {
		log.debug("accepted event: {}", sink);
		this.executor.execute(() -> {
			while(true) {
				try {
					VehicleCreatedEvent event = queue.take();
					log.debug("Sink event {} from queue", event);
					sink.next(event);
				} catch (Exception e) {
					ReflectionUtils.rethrowRuntimeException(e);
				}
			}
		});
	}

	@Override
	public void onApplicationEvent(VehicleCreatedEvent event) {
		log.debug("Event Recieved: {}", event);
		log.debug("Adding event to queue");
		this.queue.offer(event);
	}

}
