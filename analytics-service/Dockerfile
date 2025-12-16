FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

COPY ski-rental-events-contract/pom.xml ./ski-rental-events-contract/
COPY ski-rental-events-contract/src ./ski-rental-events-contract/src
RUN cd ski-rental-events-contract && mvn clean install -DskipTests -B

COPY analytics-service/pom.xml ./analytics-service/

RUN cd analytics-service && mvn dependency:go-offline -B

COPY analytics-service/src ./analytics-service/src

RUN cd analytics-service && mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN addgroup -S javauser && adduser -S javauser -G javauser

COPY --from=builder /build/analytics-service/target/*.jar app.jar

RUN chown javauser:javauser /app/app.jar

USER javauser

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
