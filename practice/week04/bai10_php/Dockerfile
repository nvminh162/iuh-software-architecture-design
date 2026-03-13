FROM php:8.2-apache

WORKDIR /var/www/html

# Bật mod_rewrite nếu cần cho các framework
RUN a2enmod rewrite

# Mã nguồn sẽ được mount từ host, nhưng vẫn đặt
# một file mặc định trong image phòng khi không mount
COPY index.php /var/www/html/index.php

EXPOSE 80

