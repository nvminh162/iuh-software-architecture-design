# Singleton 
## Khái niệm
- Là một trong nhóm Creational, nó sẽ đảm bảo duy nhất một thể thiện được tạo, hay còn có thể giải thích là xuyên suốt quá trình hoạt
động của ứng dụng chỉ có duy nhất một đối tượng được tạo ra từ một class.
## Được sử dụng trong những trường hợp nào?
- Đảm bảo chỉ có một thể hiện(instance) duy nhất của một class.
- Có thể quản lý số lượng thể hiện của một lớp trong giới hạn ta muốn.
## Nguyên tắc để tạo lên một Singleton
- Hàm tạo hay còn gọi là constructor có thể truy cập với private tức chỉ có thể truy xuất trong nội bộ của class.
- Thể hiện của nó được đặt dạng ' private static final variable' để đảm bảo biến chỉ được khởi tạo trong class.
- có một method 'public static' để trả về thể hiện được đề cập bên trên.
## Có những cách tạo ra Singleton theo từng trường hợp sử dụng
- Trên thực tế chúng ta có rất nhiều cách để tạo ra một Singleton tuy nhiên mình sẽ chỉ giới thiệu những mẫu chính và thường gặp.
- Gồm 4 trường hợp sau : Eager initialization, Lazy Initialization, Thread Safe, Double Check Locking.
- Trên đây là lý thuyết sơ bộ về singleton và những trường hợp trên mình sẽ đề cập trong video trên kênh của mình mong các bạn sẽ quan tâm.

## Ví dụ 
- ứng dụng phát nhạc: tại 1 thời điểm chỉ muốn client nghe 1 bài duy nhất

## ===

# Singleton Pattern - Java demo

## Singleton là gì?
Singleton đảm bảo trong toàn bộ ứng dụng chỉ có **1 instance duy nhất** của một class,
và mọi nơi trong hệ thống đều lấy cùng object đó qua `getInstance()`.

Ví dụ để hiểu: app phát nhạc chỉ có 1 bộ player trung tâm, không muốn tạo nhiều player
khác nhau gây xung đột trạng thái bài hát.

## 4 cách hiện thực trong project

| Kiểu | Tạo object khi nào? | Đa luồng | Ghi chú |
|---|---|---|---|
| Eager | Ngay khi class được load | Có (đơn giản) | Nhanh khi gọi, nhưng tạo sớm dù chưa cần |
| Lazy | Khi gọi lần đầu | Không | Đơn giản nhất, chỉ dùng khi single-thread |
| Thread-safe (`synchronized`) | Khi gọi lần đầu | Có | Dễ hiểu, nhưng lock mỗi lần gọi |
| Double-check locking | Khi gọi lần đầu | Có | Cân bằng giữa an toàn đa luồng và hiệu năng |

## Cấu trúc code
- `src/main/java/com/nvminh162/eager/*`
- `src/main/java/com/nvminh162/lazy/*`
- `src/main/java/com/nvminh162/thread_safe/*`
- `src/main/java/com/nvminh162/double_check_locking/*`
- `src/main/java/com/nvminh162/ComparisonMain.java` (runner tổng hợp)

Mỗi package đều có:
- Class Singleton (logic chính)
- `Main.java` (demo runnable, in hash + trạng thái dùng chung)

## Chạy nhanh

### 1) Compile
```powershell
mvn clean compile
```

### 2) Chạy từng demo
```powershell
mvn org.codehaus.mojo:exec-maven-plugin:3.6.1:java -Dexec.mainClass=com.nvminh162.eager.Main
mvn org.codehaus.mojo:exec-maven-plugin:3.6.1:java -Dexec.mainClass=com.nvminh162.lazy.Main
mvn org.codehaus.mojo:exec-maven-plugin:3.6.1:java -Dexec.mainClass=com.nvminh162.thread_safe.Main
mvn org.codehaus.mojo:exec-maven-plugin:3.6.1:java -Dexec.mainClass=com.nvminh162.double_check_locking.Main
```

### 3) Chạy 1 lệnh để so sánh cả 4 cách
```powershell
mvn org.codehaus.mojo:exec-maven-plugin:3.6.1:java -Dexec.mainClass=com.nvminh162.ComparisonMain
```

## Cách đọc output cho dễ phân biệt
- Nếu `hash` giống nhau => đang dùng cùng 1 object singleton.
- `Lazy` được ghi chú rõ là **không an toàn đa luồng**.
- `Thread-safe` và `Double-check` demo bằng nhiều thread và in số lượng hash khác nhau.
  Nếu output = `1` thì singleton được giữ đúng.
