---

# ✅ CHECKLIST – SINGLETON (PHẢI NẮM)

## 🔹 1. Definition (bắt buộc thuộc)

* Singleton là gì?
* Mục tiêu:

    * **1 instance duy nhất**
    * **global access point**

👉 Câu trả lời chuẩn:

> Singleton đảm bảo một class chỉ có một instance duy nhất và cung cấp một điểm truy cập toàn cục đến instance đó.

---

## 🔹 2. Problem (Tại sao cần Singleton?)

Bạn phải trả lời được:

### ✔ Khi nào cần:

* Quản lý tài nguyên chung:

    * DB connection
    * Logger
    * Config

### ✔ Vấn đề nếu không dùng:

* Tạo nhiều instance → conflict / tốn tài nguyên
* Không kiểm soát lifecycle

---

## 🔹 3. Core Structure (bắt buộc nhớ)

👉 3 thành phần:

* `private constructor`
* `private static instance`
* `public static getInstance()`

📌 Đây là câu hỏi lý thuyết rất hay ra

---

## 🔹 4. Lazy vs Eager Initialization

### Lazy (thường dùng)

```java
if (instance == null) → new
```

✔ tiết kiệm tài nguyên

---

### Eager

```java
private static final Singleton instance = new Singleton();
```

✔ đơn giản
❌ tạo ngay cả khi không dùng

---

## 🔹 5. Thread Safety (CỰC QUAN TRỌNG)

Bạn phải hiểu:

### ❌ Problem:

* Multi-thread → tạo nhiều instance

---

### ✔ Các cách giải:

| Cách                 | Ý tưởng      |
| -------------------- | ------------ |
| synchronized         | lock method  |
| double-check locking | check 2 lần  |
| static holder        | class loader |
| enum                 | JVM đảm bảo  |

---

👉 Câu hỏi phỏng vấn:

* Vì sao cần `volatile`?
* Double-check locking là gì?

---

## 🔹 6. Double-Checked Locking (must know)

👉 Bạn cần hiểu:

* Check 1 → tránh lock
* Check 2 → tránh tạo nhiều instance
* `volatile` → tránh reorder

---

## 🔹 7. Breaking Singleton (rất hay hỏi)

Bạn phải biết:

### ❌ 1. Reflection

→ gọi constructor private

### ❌ 2. Serialization

→ tạo object mới khi deserialize

---

### ✔ Cách fix:

* `readResolve()`
* check trong constructor
* dùng `enum`

---

## 🔹 8. Pros & Cons

### ✅ Pros

* Tiết kiệm tài nguyên
* Kiểm soát instance
* Lazy loading

---

### ❌ Cons (rất quan trọng)

* Giống global variable
* Khó test (unit test)
* Tight coupling
* Vi phạm SRP

👉 Đây là điểm ăn tiền khi phỏng vấn

---

## 🔹 9. When to Use / Not Use

### ✔ Use:

* Shared resource
* Stateless service

### ❌ Not use:

* Object có state thay đổi
* Multi-user data
* Business logic

---

## 🔹 10. Relation với SOLID

Bạn nên nói được:

* ❌ Vi phạm SRP
* ❌ Vi phạm DIP (hard dependency)
* ✔ hỗ trợ kiểm soát lifecycle

---

## 🔹 11. So sánh với thứ khác

Bạn nên biết:

* Singleton vs Static class
* Singleton vs Spring Bean
* Singleton vs Factory

---

## 🔹 12. Real-world mapping

👉 Ví dụ:

* Logger
* ConfigManager
* CacheManager

---
