## Student: Nguyễn Văn Minh - 22003405
### Week 06 - 2025 - 2026

#### Theory
```
Kiến trúc và Thiết kế Phần mềm
DHKTPM18B - 420300154902
Tiết: 10 - 12
Phòng: V4.01 (V (CS1))
GV: Nguyễn Trọng Tiến
```
#### Practice
```
Kiến trúc và Thiết kế Phần mềm
DHKTPM18B - 420300154902
Tiết: 7 - 9
Phòng: H3.1.2 (H (CS1))
GV: Nguyễn Trọng Tiến
```
#### Require week 06
```
1. Image opti + Image save data
	- Tối ưu hóa image:
			- build image: 4 stages--> 2 -->1 container
			4--> minimums
	- Image postgres--> insert data--> container--> image
		--> run container--> data?	
2. Xem: (20')
		- Database partition: (SQl Server)
					- Horizontal
					- Vertical
					- Function (logic)
		Ví dụ về 3 phần này: 
				- DB (2 table_user_01 và table_use_02)--> condition--> 01 or 02 
					(nam: table_user_01, nữ table_user_02)...> spring boot
				- Vertical
				- Function
			--> Tăng performance
	
				
3. mono--> 3 functions--> service-based architecture--> 1 DB (code BE + FE + DB)
	
4. Chủ đề: Event choreography vs orchestration.
		Bài tập
		Thiết kế workflow đặt đơn thực phẩm:
		Sử dụng Event Choreography.
		Sử dụng Event Orchestration (có 1 orchestrator).
		So sánh ưu nhược điểm.
		Quyết định mô hình phù hợp với scaling + resilience.

5. Search: springboot, reactjs, Sqlserver
		FE (js)-BE (java)-DB (stored...) (debounce)
		
6. SQL native(JDBC)-Hirbernate
```