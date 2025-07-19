FROM openjdk:21-jdk-slim

# Установка рабочей директории
WORKDIR /app

# Копируем сборку
COPY target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

# Указываем порт (опционально для докера)
EXPOSE 8761

# Запуск приложения
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]