package com.demo;

import java.net.SocketTimeoutException;
import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.demo.event.consumer.DBCircutStateEventConumer;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.EventPublisher;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;

@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CircuitBreakerConfig cbConfig() {
		return CircuitBreakerConfig.custom().failureRateThreshold(20).waitDurationInOpenState(Duration.ofMinutes(3))
				.permittedNumberOfCallsInHalfOpenState(1)
				.slidingWindow(6, 2, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
				.slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
				.recordException(t -> t instanceof SocketTimeoutException)
				.build();
	}

	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		return CircuitBreakerRegistry.of(cbConfig());
	}

	@Bean
	public CircuitBreaker circuitBreaker() {
		return circuitBreakerRegistry().circuitBreaker("dbCircut");
	}

	@Bean
	public EventPublisher eventPublisher() {

		return circuitBreaker().getEventPublisher().onStateTransition(eventConsumer());
	}

	private EventConsumer<CircuitBreakerOnStateTransitionEvent> eventConsumer() {
		return new DBCircutStateEventConumer();
	}

}
