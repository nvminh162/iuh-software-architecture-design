# Hướng dẫn Demo Kafka Message Queue

## Yêu cầu hệ thống

1. **Java 21** (đã có trong project)
2. **Apache Kafka** - Cần cài đặt và chạy Kafka server
3. **Maven** - Để build project

## Cài đặt Kafka

### Trên Windows:

1. Tải Kafka từ: https://kafka.apache.org/downloads
2. Giải nén vào thư mục (ví dụ: `C:\kafka`)
3. Mở 2 terminal windows:

**Terminal 1 - Chạy Zookeeper:**
```bash
cd C:\kafka
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```

**Terminal 2 - Chạy Kafka Server:**
```bash
cd C:\kafka
.\bin\windows\kafka-server-start.bat .\config\server.properties
```

### Trên Linux/Mac:

**Terminal 1 - Chạy Zookeeper:**
```bash
cd /path/to/kafka
bin/zookeeper-server-start.sh config/zookeeper.properties
```

**Terminal 2 - Chạy Kafka Server:**
```bash
cd /path/to/kafka
bin/kafka-server-start.sh config/server.properties
```

### Sử dụng Docker (Khuyến nghị):

```bash
docker-compose up -d
```

Tạo file `docker-compose.yml`:
```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    ports:
      - "2181:2181"

  kafka:
    image: confluentinc/cp-kafka:latest
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

## Build và chạy ứng dụng

1. **Build project:**
```bash
mvn clean install
```

2. **Chạy ứng dụng:**
```bash
mvn spring-boot:run
```

Hoặc:
```bash
java -jar target/message-queue-0.0.1-SNAPSHOT.jar
```

Ứng dụng sẽ chạy tại: `http://localhost:8080`

## Sử dụng API

### Cách 1: Sử dụng Postman Collection (Khuyến nghị)

1. **Import Postman Collection:**
   - Mở Postman
   - Click **Import** button
   - Chọn file `Kafka_Message_Queue.postman_collection.json`
   - Collection sẽ được import với tất cả các requests đã được cấu hình sẵn

2. **Sử dụng:**
   - Collection đã có biến `baseUrl` mặc định là `http://localhost:8080`
   - Bạn có thể thay đổi trong tab **Variables** của collection nếu cần
   - Chọn request và click **Send** để test

3. **Các requests có sẵn:**
   - ✅ Health Check - Kiểm tra trạng thái service
   - ✅ Send Simple Message - Gửi tin nhắn với query params
   - ✅ Send Message (JSON) - Gửi tin nhắn với JSON body
   - ✅ Send Message (Full JSON) - Gửi tin nhắn đầy đủ thông tin
   - ✅ Send Message (Vietnamese) - Gửi tin nhắn tiếng Việt có dấu

### Cách 2: Sử dụng cURL

### 1. Kiểm tra health check:
```bash
curl http://localhost:8080/api/messages/health
```

### 2. Gửi tin nhắn đơn giản:
```bash
curl -X POST "http://localhost:8080/api/messages/send-simple?content=Xin chào Kafka&sender=Nguyễn Văn Minh"
```

### 3. Gửi tin nhắn với JSON body:
```bash
curl -X POST http://localhost:8080/api/messages/send \
  -H "Content-Type: application/json" \
  -d "{
    \"content\": \"Đây là tin nhắn demo từ Kafka\",
    \"sender\": \"Minh\"
  }"
```

### 4. Gửi tin nhắn đầy đủ:
```bash
curl -X POST http://localhost:8080/api/messages/send \
  -H "Content-Type: application/json" \
  -d "{
    \"id\": \"msg-001\",
    \"content\": \"Tin nhắn đầy đủ với ID và timestamp\",
    \"sender\": \"Minh\",
    \"timestamp\": \"2024-01-15T10:30:00\"
  }"
```

## Kiểm tra logs

Khi gửi tin nhắn, bạn sẽ thấy logs trong console:

**Producer log:**
```
Gửi tin nhắn: Message(id=..., content=..., sender=..., timestamp=...)
Gửi tin nhắn thành công với offset=[...]
```

**Consumer log:**
```
═══════════════════════════════════════════════════════
Nhận được tin nhắn từ Kafka:
Topic: demo-messages
Partition: 0
Offset: ...
Message ID: ...
Sender: ...
Content: ...
Timestamp: ...
═══════════════════════════════════════════════════════
```

## Cấu trúc Project

```
src/main/java/com/nvminh162/mq/
├── MessageQueueApplication.java    # Main application
├── config/
│   └── KafkaConfig.java            # Kafka configuration
├── controller/
│   └── MessageController.java      # REST API endpoints
├── model/
│   └── Message.java                # Message model
└── service/
    ├── KafkaProducerService.java   # Producer service
    └── KafkaConsumerService.java   # Consumer service
```

## Các tính năng đã implement

✅ Kafka Producer - Gửi tin nhắn đến Kafka topic
✅ Kafka Consumer - Nhận và xử lý tin nhắn từ Kafka topic
✅ REST API - Endpoints để gửi tin nhắn
✅ JSON Serialization/Deserialization - Xử lý Message object
✅ Logging - Ghi log chi tiết khi gửi/nhận tin nhắn
✅ Manual Acknowledgment - Đảm bảo tin nhắn được xử lý đúng cách

## Troubleshooting

### Lỗi: "Connection refused" hoặc "Bootstrap broker not available"
- Đảm bảo Kafka server đang chạy tại `localhost:9092`
- Kiểm tra file `application.yaml` có đúng cấu hình không

### Lỗi: "Topic not found"
- Kafka sẽ tự động tạo topic khi có message đầu tiên
- Hoặc tạo topic thủ công:
```bash
# Windows
.\bin\windows\kafka-topics.bat --create --topic demo-messages --bootstrap-server localhost:9092

# Linux/Mac
bin/kafka-topics.sh --create --topic demo-messages --bootstrap-server localhost:9092
```

### Lỗi: "Group coordinator not available"
- Đảm bảo Zookeeper đang chạy
- Kiểm tra Kafka server đã kết nối với Zookeeper chưa
