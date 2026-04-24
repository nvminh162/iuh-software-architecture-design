package com.example.gateway;

import com.example.gateway.service.OrderGatewayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicação principal do Gateway.
 * Inicia o serviço REST e conecta ao cluster Hazelcast.
 */
@SpringBootApplication
public class GatewayApplication implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(GatewayApplication.class);
    
    @Autowired
    private OrderGatewayService orderGatewayService;
    
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("=== GATEWAY INICIADO ===");
        logger.info("Conectado ao cluster Hazelcast");
        logger.info("API REST disponível em: http://localhost:8080/api/orders");
        logger.info("Estatísticas: {}", orderGatewayService.getSystemStatistics());
        
        // Log periódico de estatísticas
        Thread statisticsThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // A cada 1 minuto
                    logger.info("Estatísticas do sistema: {}", orderGatewayService.getSystemStatistics());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        statisticsThread.setDaemon(true);
        statisticsThread.start();
    }
}
