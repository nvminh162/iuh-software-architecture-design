# 🎯 Simulando Cluster Hazelcast Localmente

Este guia mostra como simular um cluster Hazelcast distribuído no seu computador local usando diferentes abordagens.

## 🚀 Simulação (Fluxo Único Simplificado)

### 🐳 Docker Compose (recomendado)

Subir o cluster com N Processing Units (padrão 3):
```bash
./scripts/start-docker.sh        # padrão: 3 PUs
./scripts/start-docker.sh 5      # exemplo: 5 PUs
```

Parar o cluster:
```bash
./scripts/stop-docker.sh
```

Ou manualmente:
```bash
mvn clean package -DskipTests
docker compose up --build -d --scale processing-unit=3
docker compose down
```

### 3. 🔧 Execução Manual

#### Terminal 1 - Processing Unit 1:
```bash
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"
```

#### Terminal 2 - Processing Unit 2:
```bash
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"
```

#### Terminal 3 - Gateway:
```bash
cd gateway
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"
```

## 🔍 Como Observar o Cluster

### 1. Logs de Conexão
Procure por estas mensagens nos logs:

```
INFO - [192.168.1.100]:5701 [space-based-cluster] [4.2.2] 
Members {size:3, ver:3} [
    Member [192.168.1.100]:5701 - uuid1
    Member [192.168.1.100]:5702 - uuid2  
    Member [192.168.1.100]:5703 - uuid3
]
```

### 2. Processamento Distribuído
Observe como os pedidos são distribuídos:

```
Processing Unit PU-1 iniciando processamento do pedido: 123e4567-e89b-12d3-a456-426614174000
Processing Unit PU-2 iniciando processamento do pedido: 456e7890-e89b-12d3-a456-426614174001
```

### 3. Estatísticas do Cluster
```bash
curl http://localhost:8080/api/orders/statistics
```

Resposta esperada:
```
Gateway - Total: 5, Pendentes: 1, Processando: 2, Concluídos: 2
```

## 🧪 Testando a Distribuição

### 1. Criar Múltiplos Pedidos
```bash
for i in {1..10}; do
  curl -X POST http://localhost:8080/api/orders \
    -H "Content-Type: application/json" \
    -d "{\"cliente\": \"Cliente $i\", \"produto\": \"Produto $i\", \"quantidade\": $i}"
  sleep 1
done
```

### 2. Verificar Distribuição
```bash
curl http://localhost:8080/api/orders | jq '.[] | {id, processingUnitId, status}'
```

### 3. Testar Tolerância a Falhas
1. Pare uma Processing Unit
2. Continue criando pedidos
3. Observe que o sistema continua funcionando

## 📊 Monitoramento

### Docker Compose
```bash
# Ver logs de todos os serviços
docker-compose -f docker-compose.cluster.yml logs -f

# Ver logs de um serviço específico
docker-compose -f docker-compose.cluster.yml logs -f gateway

# Ver estatísticas dos containers
docker-compose -f docker-compose.cluster.yml ps
```

### Logs (Docker)
```bash
docker compose logs -f
docker compose logs -f gateway
```

## 🔧 Configurações Avançadas

### Modos de Descoberta

#### Multicast (Docker)
```yaml
hazelcast:
  discovery:
    mode: multicast
```

#### TCP/IP (Local)
```yaml
hazelcast:
  discovery:
    mode: tcp
```

### Configuração de Rede
```yaml
hazelcast:
  port: 5701
  cluster:
    name: space-based-cluster
```

## 🐛 Troubleshooting

### Problema: Serviços não se conectam
**Solução:**
1. Verifique se as portas não estão em uso
2. Use descoberta TCP/IP para desenvolvimento local
3. Verifique os logs de conexão

### Problema: Pedidos não são processados
**Solução:**
1. Verifique se as Processing Units estão rodando
2. Confirme que estão no mesmo cluster
3. Verifique os logs de processamento

### Problema: Docker não inicia
**Solução:**
1. Verifique se o Docker está rodando
2. Libere espaço em disco
3. Reinicie o Docker Desktop

## 📈 Métricas de Performance

### Tempo de Processamento
- Pedidos simples: ~2-3 segundos
- Processamento assíncrono
- Logs de início e fim

### Escalabilidade
- Adicione mais Processing Units
- Observe distribuição automática
- Teste com mais pedidos

## 🎯 Próximos Passos

1. **Adicione mais Processing Units** para testar escalabilidade
2. **Implemente persistência** para dados críticos
3. **Configure métricas** com Prometheus
4. **Teste cenários de falha** mais complexos
5. **Implemente load balancing** no Gateway

---

**💡 Dica:** Comece com o cluster Docker simples para entender os conceitos, depois evolua para o cluster completo com monitoramento!
