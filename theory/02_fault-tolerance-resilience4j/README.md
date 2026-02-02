## Demo Fault Tolerance với Resilience4j (Spring Boot)

Demo gồm **2 service Spring Boot**:

- **service-a**: đóng vai trò **client**, gọi sang service-b và áp dụng Resilience4j (`@Retry`, `@CircuitBreaker`, `@RateLimiter`, `@Bulkhead`).
- **service-b**: đóng vai trò **backend**, cố tình tạo **lỗi ngẫu nhiên** và **xử lý chậm** để test các cơ chế chịu lỗi.

### 1. Chuẩn bị môi trường

- **JDK**: 21
- **Maven**: 3.9+

### 2. Chạy từng service

- **Service B (port 8082)**  
  Mở terminal tại thư mục `service-b`:

  ```bash
  mvn spring-boot:run
  ```

- **Service A (port 8081)**  
  Mở terminal khác tại thư mục `service-a`:

  ```bash
  mvn spring-boot:run
  ```

### 3. Các endpoint chính

- **Service B** (`http://localhost:8082`):
  - `GET /api/b/ok` – luôn trả về OK.
  - `GET /api/b/maybe-error` – **thỉnh thoảng ném exception** (mô phỏng lỗi tạm thời).
  - `GET /api/b/slow` – **sleep 4s** (mô phỏng chậm/trễ).

- **Service A** (`http://localhost:8081`) – tất cả đều gọi sang B:
  - `GET /api/a/retry` – dùng **`@Retry`** để tự retry khi B lỗi ngẫu nhiên.
  - `GET /api/a/cb` – dùng **`@CircuitBreaker`** để **ngắt mạch** khi B lỗi nhiều.
  - `GET /api/a/rate-limiter` – dùng **`@RateLimiter`** để **giới hạn số request/giây**.
  - `GET /api/a/bulkhead` – dùng **`@Bulkhead`** để **giới hạn số request đồng thời** tới B (bảo vệ A khỏi nghẽn).

Khi Resilience4j chặn hoặc gặp lỗi, service A sẽ trả về **chuỗi fallback** (ví dụ: `"Fallback RETRY từ A..."`), giúp hệ thống vẫn đáp ứng thay vì sập hẳn.

### 4. Test bằng Postman

- Import file `postman.json` vào Postman.
- Trong collection sẽ có sẵn:
  - Nhóm request **Service B** (gọi trực tiếp B).
  - Nhóm request **Service A** (gọi qua A để thấy rõ hành vi Retry/CB/RateLimiter/Bulkhead).

### 5. Ý tưởng demo cho báo cáo

- **Retry**: spam `GET /api/a/retry`, xem log B thỉnh thoảng lỗi nhưng A retry rồi vẫn trả về kết quả hoặc fallback.
- **Circuit Breaker**: gọi `GET /api/a/cb` nhiều lần khi B hay lỗi → sau ngưỡng, mạch mở, A trả về fallback ngay mà **không gọi B nữa**.
- **Rate Limiter (client)**: bắn nhanh `GET /api/a/rate-limiter` → sau giới hạn, A trả về thông báo fallback vì bị giới hạn tần suất.
- **Bulkhead**: mở nhiều tab/Postman runner với `GET /api/a/bulkhead` → một số request bị trả về fallback vì vượt quá số slot xử lý cùng lúc.
