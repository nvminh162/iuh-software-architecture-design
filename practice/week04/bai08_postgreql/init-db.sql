-- Script tạo database và user mẫu
CREATE DATABASE sampledb;

CREATE USER sampleuser WITH ENCRYPTED PASSWORD 'samplepass';

GRANT ALL PRIVILEGES ON DATABASE sampledb TO sampleuser;

