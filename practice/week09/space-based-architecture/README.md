# 🚀 Space-Based Architecture Demo

Este projeto demonstra uma implementação prática de **Space-Based Architecture (SBA)** usando Java, Spring Boot e Hazelcast. O sistema simula um ambiente distribuído com Processing Units (PUs) e um Data Grid em memória para gerenciamento de pedidos.

## 📋 Visão Geral

A **Space-Based Architecture** é um padrão arquitetural que utiliza um **espaço de dados compartilhado em memória** para comunicação entre componentes distribuídos. Neste exemplo:

- **Gateway**: API REST que recebe requisições e interage com o espaço distribuído
- **Processing Units**: Serviços que processam pedidos e armazenam dados no cluster Hazelcast
- **Data Grid**: Hazelcast como espaço distribuído em memória para compartilhamento de dados

## 🏗️ Arquitetura

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   Gateway   │    │     PU-1    │    │     PU-2    │
│  (API REST) │    │ (Processing │    │ (Processing │
│             │    │    Unit)    │    │    Unit)    │
└──────┬──────┘    └──────┬──────┘    └──────┬──────┘
       │                  │                  │
       └──────────────────┼──────────────────┘
                          │
                ┌─────────▼─────────┐
                │   Hazelcast       │
                │  Data Grid        │
                │ (Shared Space)    │
                └───────────────────┘
```

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.2.0**
- **Maven** (Multi-módulo)
- **Hazelcast 5.3.6** (Data Grid distribuído)
- **Docker & Docker Compose**

## 📁 Estrutura do Projeto

```
space-based-architecture-demo/
├── pom.xml                          # POM principal
├── docker-compose.yml               # Orquestração de containers
├── docker-compose.dev.yml           # Configuração de desenvolvimento
├── common/                          # Módulo compartilhado
│   ├── pom.xml
│   └── src/main/java/com/example/common/
│       ├── entity/                  # Entidades (Order, OrderStatus)
│       └── dto/                     # DTOs (CreateOrderRequest, OrderResponse)
├── gateway/                         # API REST Gateway
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/main/java/com/example/gateway/
│       ├── config/                  # Configuração Hazelcast
│       ├── controller/              # REST Controllers
│       ├── service/                 # Serviços de negócio
│       └── GatewayApplication.java
└── processing-unit/                 # Processing Unit
    ├── pom.xml
    ├── Dockerfile
    └── src/main/java/com/example/processing/
        ├── config/                  # Configuração Hazelcast
        ├── service/                 # Serviços de processamento
        └── ProcessingUnitApplication.java
```

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose (opcional)

### 🎯 Execução Simplificada do Cluster Hazelcast (Docker Compose Único)

Suba o cluster com escala de Processing Units ajustável via parâmetro (padrão 3):
```bash
./scripts/start-docker.sh           # escala padrão: 3
./scripts/start-docker.sh 5         # exemplo com 5 PUs
```

Para parar:
```bash
./scripts/stop-docker.sh
```

Alternativamente, você pode usar o Docker Compose diretamente:
```bash
mvn clean package -DskipTests
docker compose up --build -d --scale processing-unit=3
docker compose down
```

#### 🔧 Execução Manual (sem Docker)

**Compilar o projeto:**
```bash
mvn clean install
```

**Executar Processing Units (terminais separados):**
```bash
# Terminal 1
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"

# Terminal 2  
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"

# Terminal 3
cd gateway
mvn spring-boot:run -Dspring-boot.run.arguments="--hazelcast.discovery.mode=tcp"
```

### 📊 Acessos

| Serviço | URL | Descrição |
|---------|-----|-----------|
| Gateway API | http://localhost:8080 | API REST principal |
| Management Center | — | (opcional, não incluído por padrão) |

### ⏹️ Parar os Serviços

**Docker:** use `./scripts/stop-docker.sh` ou `docker compose down`

## 📡 API Endpoints

### Gateway (http://localhost:8080)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/orders` | Criar novo pedido |
| `GET` | `/api/orders/{id}` | Buscar pedido por ID |
| `GET` | `/api/orders` | Listar todos os pedidos |
| `PUT` | `/api/orders/{id}/status` | Atualizar status do pedido |
| `GET` | `/api/orders/statistics` | Obter estatísticas do sistema |
| `GET` | `/api/orders/health` | Health check |

### Exemplos de Uso

#### Criar um pedido:
```bash
curl -X POST "http://localhost:8080/api/orders" -H "Content-Type: application/json" -d "{\"cliente\":\"Joao Silva\",\"produto\":\"Notebook Dell\",\"quantidade\":1}"
```

#### Buscar um pedido:
```bash
curl -X GET "http://localhost:8080/api/orders/{order-id}"
```

#### Listar todos os pedidos:
```bash
curl -X GET "http://localhost:8080/api/orders"
```

#### Atualizar status do pedido:
```bash
curl -X PUT "http://localhost:8080/api/orders/{order-id}/status?status=CONCLUIDO"
```

#### Obter estatísticas:
```bash
curl -X GET "http://localhost:8080/api/orders/statistics"
```

#### Health check:
```bash
curl -X GET "http://localhost:8080/api/orders/health"
```

## 🔍 Observando o Comportamento Distribuído

### 1. Múltiplas Instâncias

Para observar o comportamento distribuído, execute múltiplas instâncias do Processing Unit:

```bash
# Terminal 1 - Processing Unit 1
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

# Terminal 2 - Processing Unit 2  
cd processing-unit
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8082"

# Terminal 3 - Gateway
cd gateway
mvn spring-boot:run
```

### 2. Logs e Monitoramento

Os logs mostrarão:
- Conexão ao cluster Hazelcast
- Processamento de pedidos pelas PUs
- Replicação de dados no espaço distribuído
- Estatísticas em tempo real

### 3. Testando a Distribuição

1. **Crie pedidos** via API
2. **Observe os logs** das Processing Units
3. **Verifique as estatísticas** para ver a distribuição
4. **Pare uma PU** e veja como o sistema continua funcionando

## 🐳 Docker Compose

### Serviços Disponíveis

- **gateway**: API REST na porta 8080
- **processing-unit-1**: Primeira Processing Unit
- **processing-unit-2**: Segunda Processing Unit  
- **processing-unit-3**: Terceira Processing Unit

### Comandos Úteis

```bash
# Ver logs de todos os serviços
docker-compose logs -f

# Ver logs de um serviço específico
docker-compose logs -f gateway

# Escalar Processing Units
docker-compose up --scale processing-unit=5

# Reiniciar um serviço
docker-compose restart gateway
```

## 📊 Monitoramento

### Health Checks

- Gateway: http://localhost:8080/actuator/health
- Processing Units: Logs no console

### Métricas

- Estatísticas do sistema via API
- Logs de processamento em tempo real
- Informações do cluster Hazelcast

## 🔧 Configuração

### Variáveis de Ambiente

| Variável | Descrição | Padrão |
|----------|-----------|---------|
| `HAZELCAST_PORT` | Porta do Hazelcast | 5701 |
| `HAZELCAST_CLUSTER_NAME` | Nome do cluster | space-based-cluster |
| `PROCESSING_UNIT_ID` | ID da Processing Unit | PU-${random.uuid} |
| `SPRING_PROFILES_ACTIVE` | Perfil ativo | dev |

### Perfis Spring

- **dev**: Desenvolvimento com logs detalhados
- **prod**: Produção com logs otimizados
- **docker**: Configuração para containers

## 🧪 Testando o Sistema

### Cenário 1: Criação de Pedidos
1. Execute o sistema
2. Crie vários pedidos via API
3. Observe como as PUs processam os pedidos
4. Verifique as estatísticas

### Cenário 2: Tolerância a Falhas
1. Execute múltiplas PUs
2. Crie pedidos
3. Pare uma PU durante o processamento
4. Verifique se o sistema continua funcionando

### Cenário 3: Escalabilidade
1. Execute com 1 PU
2. Crie muitos pedidos
3. Adicione mais PUs
4. Observe a distribuição de carga

## 📝 Logs Importantes

```
=== GATEWAY INICIADO ===
Conectado ao cluster Hazelcast
API REST disponível em: http://localhost:8080/api/orders

=== PROCESSING UNIT INICIADO ===
Conectado ao cluster Hazelcast
Aguardando pedidos para processamento...

Processing Unit PU-1 iniciando processamento do pedido: 123e4567-e89b-12d3-a456-426614174000
Pedido 123e4567-e89b-12d3-a456-426614174000 salvo no espaço distribuído pela PU PU-1
Pedido 123e4567-e89b-12d3-a456-426614174000 processado com sucesso pela PU PU-1
```

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 🆘 Suporte

Para dúvidas ou problemas:
1. Verifique os logs dos serviços
2. Consulte a documentação do Hazelcast
3. Abra uma issue no repositório

---

**🎯 Objetivo**: Este projeto demonstra como implementar uma arquitetura baseada em espaço usando Java, mostrando os conceitos de distribuição, tolerância a falhas e escalabilidade em um ambiente prático e funcional.
