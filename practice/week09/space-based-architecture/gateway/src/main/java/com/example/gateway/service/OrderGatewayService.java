package com.example.gateway.service;

import com.example.common.dto.CreateOrderRequest;
import com.example.common.dto.OrderResponse;
import com.example.common.entity.Order;
import com.example.common.entity.OrderStatus;
import com.hazelcast.map.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Serviço do Gateway responsável por gerenciar pedidos.
 * Atua como ponto de entrada para o sistema distribuído.
 */
@Service
public class OrderGatewayService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderGatewayService.class);
    
    @Autowired
    private IMap<String, Order> ordersMap;
    
    /**
     * Cria um novo pedido no sistema distribuído.
     * 
     * @param request Dados do pedido
     * @return Pedido criado
     */
    public OrderResponse createOrder(CreateOrderRequest request) {
        logger.info("Gateway recebendo novo pedido: {}", request);
        
        // Cria a entidade Order
        Order order = new Order(request.getCliente(), request.getProduto(), request.getQuantidade());
        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.PENDENTE);
        
        // Salva no espaço distribuído
        ordersMap.put(order.getId(), order);
        
        logger.info("Pedido {} criado e salvo no espaço distribuído", order.getId());
        
        return new OrderResponse(order);
    }
    
    /**
     * Recupera um pedido pelo ID.
     * 
     * @param orderId ID do pedido
     * @return Pedido encontrado ou null
     */
    public OrderResponse getOrder(String orderId) {
        logger.info("Gateway buscando pedido: {}", orderId);
        
        Order order = ordersMap.get(orderId);
        
        if (order != null) {
            logger.info("Pedido {} encontrado no espaço distribuído", orderId);
            return new OrderResponse(order);
        } else {
            logger.warn("Pedido {} não encontrado no espaço distribuído", orderId);
            return null;
        }
    }
    
    /**
     * Lista todos os pedidos do sistema.
     * 
     * @return Lista de pedidos
     */
    public java.util.List<OrderResponse> getAllOrders() {
        logger.info("Gateway listando todos os pedidos do espaço distribuído");
        
        return ordersMap.values().stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }
    
    /**
     * Atualiza o status de um pedido.
     * 
     * @param orderId ID do pedido
     * @param status Novo status
     * @return Pedido atualizado ou null
     */
    public OrderResponse updateOrderStatus(String orderId, OrderStatus status) {
        logger.info("Gateway atualizando status do pedido {} para {}", orderId, status);
        
        Order order = ordersMap.get(orderId);
        
        if (order != null) {
            order.setStatus(status);
            ordersMap.put(orderId, order);
            logger.info("Status do pedido {} atualizado para {}", orderId, status);
            return new OrderResponse(order);
        } else {
            logger.warn("Pedido {} não encontrado para atualização", orderId);
            return null;
        }
    }
    
    /**
     * Obtém estatísticas do sistema distribuído.
     * 
     * @return String com estatísticas
     */
    public String getSystemStatistics() {
        int totalOrders = ordersMap.size();
        long pendingOrders = ordersMap.values().stream()
                .filter(order -> order.getStatus() == OrderStatus.PENDENTE)
                .count();
        long processingOrders = ordersMap.values().stream()
                .filter(order -> order.getStatus() == OrderStatus.PROCESSANDO)
                .count();
        long completedOrders = ordersMap.values().stream()
                .filter(order -> order.getStatus() == OrderStatus.CONCLUIDO)
                .count();
        
        return String.format("Gateway - Total: %d, Pendentes: %d, Processando: %d, Concluídos: %d", 
                           totalOrders, pendingOrders, processingOrders, completedOrders);
    }
}
