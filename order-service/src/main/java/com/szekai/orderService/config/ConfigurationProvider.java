package com.szekai.orderService.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.retry.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
//@LoadBalancerClient(name = "api-gateway", configuration = LBExampleConfiguration.class)
@LoadBalancerClient(value = "order-service", configuration = ReactorLoadBalancerConfig.class)
public class ConfigurationProvider {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

//    @Bean
//    public RegistryEventConsumer<Retry> retryRegistryEventConsumer(){
//        return new RegistryEventConsumer<>() {
//
//            @Override
//            public void onEntryAddedEvent(EntryAddedEvent<Retry> entryAddedEvent) {
//                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> log.info(event.toString()));
//            }
//
//            @Override
//            public void onEntryRemovedEvent(EntryRemovedEvent<Retry> entryRemoveEvent) {
//
//            }
//
//            @Override
//            public void onEntryReplacedEvent(EntryReplacedEvent<Retry> entryReplacedEvent) {
//
//            }
//        };
//    }

//    @Bean
//    public RegistryEventConsumer<CircuitBreaker> circuitBreakerRegistryEventConsumer(){
//        return new RegistryEventConsumer<>() {
//
//            @Override
//            public void onEntryAddedEvent(EntryAddedEvent<CircuitBreaker> entryAddedEvent) {
//                entryAddedEvent.getAddedEntry().getEventPublisher().onEvent(event -> log.info(event.toString()));
//            }
//
//            @Override
//            public void onEntryRemovedEvent(EntryRemovedEvent<CircuitBreaker> entryRemoveEvent) {
//
//            }
//
//            @Override
//            public void onEntryReplacedEvent(EntryReplacedEvent<CircuitBreaker> entryReplacedEvent) {
//
//            }
//        };
//
//    }

}
