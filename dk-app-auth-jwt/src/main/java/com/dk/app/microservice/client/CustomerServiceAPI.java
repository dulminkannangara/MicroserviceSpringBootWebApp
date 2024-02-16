package com.dk.app.microservice.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class CustomerServiceAPI {
    @Autowired
    private DiscoveryClient client;

    public String getCustomerAPIUrl() {
        // get ServiceInstance list using serviceId
        List<ServiceInstance> siList = client.getInstances("CUSTOMER-SERVICE");

        //get random api
        Random rand = new Random();
        ServiceInstance randomElement = siList.get(rand.nextInt(siList.size()));
        // read URI and Add path that returns url
        return randomElement.getUri()+"";
    }


}
