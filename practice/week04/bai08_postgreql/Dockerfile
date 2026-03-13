FROM postgres:15

ENV POSTGRES_USER=admin
ENV POSTGRES_PASSWORD=adminpass
ENV POSTGRES_DB=maindb

# Tất cả file .sql trong thư mục này sẽ được chạy
# tự động khi container khởi tạo lần đầu
COPY init-db.sql /docker-entrypoint-initdb.d/init-db.sql

EXPOSE 5432

