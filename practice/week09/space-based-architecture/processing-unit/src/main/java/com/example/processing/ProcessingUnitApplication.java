package com.example.processing;

import com.example.processing.service.OrderProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Aplicação principal do Processing Unit.
 * Inicia o serviço de processamento e conecta ao cluster Hazelcast.
 */
@SpringBootApplication
@EnableAsync
public class ProcessingUnitApplication implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessingUnitApplication.class);
    
    @Autowired
    private OrderProcessingService orderProcessingService;
    
    public static void main(String[] args) {
        SpringApplication.run(ProcessingUnitApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        logger.info("=== PROCESSING UNIT INICIADO ===");
        logger.info("Conectado ao cluster Hazelcast");
        logger.info("Aguardando pedidos para processamento...");
        logger.info("Estatísticas: {}", orderProcessingService.getSpaceStatistics());
        
        // Log periódico de estatísticas
        Thread statisticsThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000); // A cada 30 segundos
                    logger.info("Estatísticas do espaço: {}", orderProcessingService.getSpaceStatistics());
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
