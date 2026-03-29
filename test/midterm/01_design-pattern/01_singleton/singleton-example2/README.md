# 📘 Singleton Pattern – Exam Cheat Sheet (Java)

## 🎯 1. Definition (PHẢI THUỘC)

Singleton là creational design pattern đảm bảo:

- Chỉ có **1 instance duy nhất** của một class
- Cung cấp **global access point** đến instance đó

---

## 🧠 2. Khi nào dùng?

Dùng khi cần:

- Database connection
- Logger
- Configuration
- Cache

👉 Điểm chung: tài nguyên dùng chung toàn hệ thống

---

## ⚙️ 3. Cấu trúc bắt buộc (3 thành phần)

### 1. Static instance

```java
private static Database instance;
```

- static → dùng chung toàn chương trình

### 2. Private constructor

```java
private Database() {}
```

- Ngăn tạo object bằng `new`

### 3. getInstance()

```java
public static Database getInstance() {
    if (instance == null) {
        instance = new Database();
    }
    return instance;
}
```

- Tạo object khi cần (lazy initialization)
- Luôn trả về cùng 1 instance

---

## 🔥 4. Nguyên lý hoạt động

- Lần đầu gọi → instance = null → tạo object
- Lần sau → trả về object đã tạo

👉 Kết quả:

```java
Database a = Database.getInstance();
Database b = Database.getInstance();

// a == b → true
```

---

## ⚠️ 5. Vấn đề Thread-safe (RẤT QUAN TRỌNG)

### ❌ Code KHÔNG thread-safe

```java
if (instance == null) {
    instance = new Database();
}
```

👉 Nếu nhiều thread:

- Thread A thấy null
- Thread B cũng thấy null
  → tạo 2 object ❌

---

### ✅ Cách fix (Double-check locking)

```java
public static Database getInstance() {
    if (instance == null) {
        synchronized (Database.class) {
            if (instance == null) {
                instance = new Database();
            }
        }
    }
    return instance;
}
```

---

## ⚡ 6. Lazy vs Eager

### Lazy Initialization

- Tạo khi cần
- Tiết kiệm tài nguyên
- Phải xử lý thread-safe

### Eager Initialization

```java
private static Database instance = new Database();
```

- Tạo ngay từ đầu
- Thread-safe tự nhiên
- Tốn tài nguyên nếu không dùng

---

## 🧪 7. Câu hỏi hay ra thi

### Lý thuyết

- Singleton là gì?
- Tại sao constructor phải private?
- Tại sao dùng static?
- Lazy vs Eager?
- Thread-safe là gì?

### Code

- Viết Singleton
- Fix lỗi thread-safe
- Giải thích `foo == bar`

---

## ❌ 8. Nhược điểm

- Vi phạm Single Responsibility Principle
- Khó test (mock)
- Dễ bị lạm dụng như global variable
- Phức tạp khi xử lý multithread

---

## 🧠 9. Checklist trước khi đi thi

Bạn phải làm được:

☑ Viết Singleton từ đầu (không nhìn tài liệu)
☑ Giải thích từng dòng code
☑ Phân biệt lazy vs eager
☑ Giải thích thread-safe
☑ Nêu ví dụ thực tế
☑ Nêu ưu / nhược điểm

---

## 🚀 10. Mẹo nhớ nhanh

"Singleton = 1 object + global access"

3 thứ bắt buộc:

- static instance
- private constructor
- getInstance()

---

👉 Nếu bạn hiểu hết file này = đủ ăn điểm cao bài Singleton 💯
