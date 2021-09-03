package com.szekai.orderService.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.loadbalancer.core.RandomLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class LBExampleConfiguration {
//    @Bean
//    @Primary
//    ServiceInstanceListSupplier serviceInstanceListSupplier() {
//        return new IMServiceInstanceListSuppler("load-balancer");
//    }

//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment,
//                                                                                   LoadBalancerClientFactory loadBalancerClientFactory) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new RandomLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class), name);
//    }


}

@Slf4j
class IMServiceInstanceListSuppler implements ServiceInstanceListSupplier {
    private final String serviceId;

    @Value("#{'${api.endpoints}'.split(',')}")
    private List<String> endpoints;

    private List<ServiceInstance> instances;

    @Autowired
    DiscoveryClient discoveryClient;

    IMServiceInstanceListSuppler(String serviceId) {
        this.serviceId = serviceId;
    }

//    @PostConstruct
//    void buildServiceInstance(){
//        instances = new ArrayList<>();
//        Integer sid = 0;
//
//        for (String host : endpoints) {
//            sid++;
//            String[] hosts = host.split(":");
//            instances.add(new DefaultServiceInstance(serviceId + sid, serviceId, "localhost", Integer.parseInt(hosts[1]), false));
//            log.info(" --- Added Host: " + hosts[0] + ":port " + hosts[1]);
//        }

//        instances = discoveryClient.getInstances("payment-service");
//        instances.forEach(x -> {
//            log.info("Service Instance " + x.toString());
//        });
//    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        instances = discoveryClient.getInstances("payment-service");
        instances.forEach(x -> {
            log.info("Service Instance " + x.toString());
        });
        return Flux.just(instances);
    }
}
