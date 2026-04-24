package com.example.processing.service;

import com.example.common.entity.Order;
import com.example.common.entity.OrderStatus;
import com.hazelcast.map.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Serviço responsável por processar pedidos no espaço distribuído.
 * Simula o processamento assíncrono de pedidos em uma Processing Unit.
 */
@Service
public class OrderProcessingService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);
    
    @Autowired
    private IMap<String, Order> ordersMap;
    
    @Value("${processing.unit.id:PU-${random.uuid}}")
    private String processingUnitId;
    
    /**
     * Processa um pedido no espaço distribuído.
     * 
     * @param order Pedido a ser processado
     * @return Pedido processado
     */
    public Order processOrder(Order order) {
        logger.info("Processing Unit {} iniciando processamento do pedido: {}", 
                   processingUnitId, order.getId());
        
        // Define o ID da Processing Unit que está processando
        order.setProcessingUnitId(processingUnitId);
        order.setStatus(OrderStatus.PROCESSANDO);
        
        // Salva no espaço distribuído
        ordersMap.put(order.getId(), order);
        
        logger.info("Pedido {} salvo no espaço distribuído pela PU {}", 
                   order.getId(), processingUnitId);
        
        // Simula processamento assíncrono
        CompletableFuture.runAsync(() -> {
            try {
                // Simula tempo de processamento
                Thread.sleep(2000 + (long) (Math.random() * 3000));
                
                // Atualiza status para concluído
                order.setStatus(OrderStatus.CONCLUIDO);
                ordersMap.put(order.getId(), order);
                
                logger.info("Pedido {} processado com sucesso pela PU {}", 
                           order.getId(), processingUnitId);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Erro ao processar pedido {}: {}", order.getId(), e.getMessage());
                
                // Marca como cancelado em caso de erro
                order.setStatus(OrderStatus.CANCELADO);
                ordersMap.put(order.getId(), order);
            }
        });
        
        return order;
    }
    
    /**
     * Recupera um pedido do espaço distribuído.
     * 
     * @param orderId ID do pedido
     * @return Pedido encontrado ou null
     */
    public Order getOrder(String orderId) {
        logger.info("PU {} buscando pedido {} no espaço distribuído", 
                   processingUnitId, orderId);
        
        Order order = ordersMap.get(orderId);
        
        if (order != null) {
            logger.info("Pedido {} encontrado no espaço distribuído", orderId);
        } else {
            logger.warn("Pedido {} não encontrado no espaço distribuído", orderId);
        }
        
        return order;
    }
    
    /**
     * Lista todos os pedidos no espaço distribuído.
     * 
     * @return Mapa com todos os pedidos
     */
    public IMap<String, Order> getAllOrders() {
        logger.info("PU {} listando todos os pedidos do espaço distribuído", processingUnitId);
        return ordersMap;
    }
    
    /**
     * Obtém estatísticas do espaço distribuído.
     * 
     * @return String com estatísticas
     */
    public String getSpaceStatistics() {
        int totalOrders = ordersMap.size();
        long processingOrders = ordersMap.values().stream()
                .filter(order -> order.getStatus() == OrderStatus.PROCESSANDO)
                .count();
        long completedOrders = ordersMap.values().stream()
                .filter(order -> order.getStatus() == OrderStatus.CONCLUIDO)
                .count();
        
        return String.format("PU %s - Total: %d, Processando: %d, Concluídos: %d", 
                           processingUnitId, totalOrders, processingOrders, completedOrders);
    }
}
