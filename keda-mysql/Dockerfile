FROM php:8.1.8-apache-bullseye

COPY index.php /var/www/html/
# Add the pdo_mysql extension
RUN apt-get update && docker-php-ext-install pdo_mysql

EXPOSE 80