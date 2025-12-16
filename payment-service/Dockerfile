FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY payment-service/pom.xml ./payment-service/

RUN cd payment-service && mvn dependency:go-offline -B

COPY payment-service/src ./payment-service/src

RUN cd payment-service && mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S javauser && adduser -S javauser -G javauser

COPY --from=builder /build/payment-service/target/*.jar app.jar

RUN chown javauser:javauser /app/app.jar

USER javauser

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
