# Nguyễn Văn Minh - Compare

```
REST - gRPC - GraphQL
```

# 1. REST – “Ông vua phổ thông” 👑

## Bản chất

* Giao tiếp qua **HTTP**
* Dữ liệu thường là **JSON**
* Mỗi resource = 1 URL

```http
GET /api/users/1
GET /api/users/1/orders
```

---

## Ví dụ

### Client gọi

```http
GET /orders/123
```

### Response

```json
{
  "id": 123,
  "total": 500000,
  "items": [...]
}
```

---

## Ưu điểm

✅ Dễ hiểu
✅ Dễ debug (curl, browser)
✅ Chuẩn industry
✅ Hợp với frontend (React, Next, Mobile)

---

## Nhược điểm

❌ Over-fetch / Under-fetch
❌ Payload JSON to → chậm hơn gRPC
❌ Không tối ưu cho internal service call lớn

---

## Khi nào nên dùng REST? (quan điểm mình)

✔️ Public API
✔️ Frontend ↔ Backend
✔️ CRUD
✔️ Team đông, junior nhiều
✔️ Cần debug nhanh

📌 **90% web app nên dùng REST**

---

# 2. gRPC – “Sát thủ performance” ⚡

## Bản chất

* HTTP/2
* Binary (Protocol Buffers)
* Contract-first (`.proto`)
* Không dành cho browser (trừ khi có gRPC-web)

---

## Ví dụ

### file `payment.proto`

```proto
service PaymentService {
  rpc Pay (PayRequest) returns (PayResponse);
}
```

### Java gọi

```java
paymentServiceStub.pay(request);
```

---

## Ưu điểm

🔥 Rất nhanh
🔥 Payload nhỏ
🔥 Type-safe tuyệt đối
🔥 Streaming tốt (real-time)

---

## Nhược điểm

❌ Debug khó (không curl được)
❌ Browser support kém
❌ Learning curve cao
❌ Không thân thiện với frontend

---

## Khi nào nên dùng gRPC?

✔️ Service ↔ Service (internal)
✔️ High traffic
✔️ Low latency
✔️ Event / Streaming
✔️ Microservice lớn

📌 **Netflix, Uber, Google dùng gRPC cho nội bộ**

---

# 3. GraphQL – “Frontend làm chủ cuộc chơi” 🎯

## Bản chất

* 1 endpoint duy nhất
* Client quyết định dữ liệu cần
* Backend chỉ expose schema

---

## Ví dụ

### Client query

```graphql
query {
  user(id: 1) {
    name
    orders {
      id
      total
    }
  }
}
```

### Response

```json
{
  "data": {
    "user": {
      "name": "Minh",
      "orders": [
        { "id": 1, "total": 200000 }
      ]
    }
  }
}
```

---

## Ưu điểm

✅ Không over-fetch
✅ Frontend cực sướng
✅ Ít endpoint
✅ Phù hợp UI phức tạp

---

## Nhược điểm

❌ Backend phức tạp
❌ Khó cache
❌ Dễ bị query quá nặng
❌ Debug production khó

---

## Khi nào nên dùng GraphQL?

✔️ Frontend phức tạp
✔️ Mobile app nhiều version
✔️ Nhiều client khác nhau
✔️ BFF (Backend for Frontend)

📌 **Facebook, GitHub API dùng GraphQL**

---

# 4. So sánh nhanh (thực tế)

| Tiêu chí     | REST  | gRPC    | GraphQL            |
| ------------ | ----- | ------- | ------------------ |
| Dễ học       | ⭐⭐⭐⭐⭐ | ⭐⭐      | ⭐⭐⭐                |
| Performance  | ⭐⭐⭐   | ⭐⭐⭐⭐⭐   | ⭐⭐⭐                |
| Browser      | ✅     | ❌       | ✅                  |
| Type-safe    | ❌     | ✅       | ⚠️                 |
| Debug        | Dễ    | Khó     | Trung bình         |
| Microservice | Tạm   | Rất tốt | Không khuyến khích |

---

# 5. Quan điểm cá nhân (quan trọng nhất)

### ❌ Sai lầm phổ biến

> “Dùng gRPC cho tất cả cho ngầu”

→ ❌ **Sai**
→ Phức tạp + không cần thiết

---

### ✅ Cách dùng mình thấy hợp lý nhất

```
Frontend
   ↓
GraphQL hoặc REST
   ↓
Backend (BFF / API Gateway)
   ↓
gRPC
   ↓
Microservices
```

---

### Tóm gọn theo use case

* **CRUD web app** → REST
* **Internal microservice** → gRPC
* **UI phức tạp, mobile nhiều version** → GraphQL
* **Startup nhỏ / solo dev** → REST (đừng nghĩ nhiều)

---

# 6. Một câu chốt (đi phỏng vấn rất ăn điểm)

> **Không có công nghệ tốt nhất, chỉ có công nghệ phù hợp nhất với bài toán.**

Nếu bạn muốn, mình có thể:

* Viết **demo cùng 1 bài toán bằng REST vs gRPC**
* Vẽ **sơ đồ kiến trúc microservice chuẩn**
* Giúp bạn **chọn stack cho đồ án / production**

Bạn muốn đào sâu hướng nào tiếp? 🚀
