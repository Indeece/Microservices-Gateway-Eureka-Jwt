FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/income-expense-service-0.0.1-SNAPSHOT.jar income-expense-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "income-expense-service.jar"]