CREATE DATABASE IF NOT EXISTS lab05_search_demo;
USE lab05_search_demo;

CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

INSERT INTO product (name, description) VALUES
('Apple iPhone 15 Pro', 'Latest flagship phone from Apple with A17 Pro chip.'),
('Samsung Galaxy S24 Ultra', 'Samsung flagship with advanced AI features and S Pen.'),
('Google Pixel 8 Pro', 'Google phone with incredible camera and pure Android experience.'),
('Sony Alpha 7R V', 'High-resolution full-frame mirrorless camera.'),
('MacBook Pro M3 Max', 'Powerful laptop for professionals from Apple.'),
('Dell XPS 15', 'Premium Windows laptop with a beautiful OLED display.'),
('Sony PlayStation 5', 'Next-gen console with immersive gaming experience.'),
('Xbox Series X', 'Powerful gaming console from Microsoft.'),
('Nintendo Switch OLED', 'Hybrid gaming console with vibrant OLED screen.'),
('Keychron Q1 Pro', 'Wireless custom mechanical keyboard.');

DELIMITER //

DROP PROCEDURE IF EXISTS search_products //

CREATE PROCEDURE search_products(IN search_term VARCHAR(255))
BEGIN
    SELECT id, name, description 
    FROM product
    WHERE name LIKE CONCAT('%', search_term, '%') 
       OR description LIKE CONCAT('%', search_term, '%');
END //

DELIMITER ;
