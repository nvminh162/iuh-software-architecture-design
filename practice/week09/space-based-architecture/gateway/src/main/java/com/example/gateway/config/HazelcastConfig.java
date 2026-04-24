package com.example.gateway.config;

import com.example.common.entity.Order;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Hazelcast para o Gateway.
 * Conecta ao mesmo cluster do Processing Unit.
 */
@Configuration
public class HazelcastConfig {
    
    @Value("${hazelcast.cluster.name:space-based-cluster}")
    private String clusterName;
    
    @Value("${hazelcast.client.addresses:18.142.254.111:5701,18.142.254.111:5702,18.142.254.111:5703}")
    private String clientAddresses;
    
    @Bean
    public HazelcastInstance hazelcastInstance() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClusterName(clusterName);
        ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();
        for (String addr : clientAddresses.split(",")) {
            networkConfig.addAddress(addr.trim());
        }
        clientConfig.setInstanceName("gateway-client-" + System.currentTimeMillis());
        clientConfig.setProperty("hazelcast.logging.type", "slf4j");
        return HazelcastClient.newHazelcastClient(clientConfig);
    }
    
    @Bean
    public IMap<String, Order> ordersMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("orders");
    }
}
