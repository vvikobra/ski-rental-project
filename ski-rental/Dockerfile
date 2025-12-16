FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /build

COPY ski-rental-events-contract/pom.xml ./ski-rental-events-contract/
COPY ski-rental-events-contract/src ./ski-rental-events-contract/src
RUN cd ski-rental-events-contract && mvn clean install -DskipTests -B

COPY ski-rental-contracts/pom.xml ./ski-rental-contracts/
COPY ski-rental-contracts/src ./ski-rental-contracts/src
RUN cd ski-rental-contracts && mvn clean install -DskipTests -B

COPY ski-rental/pom.xml ./ski-rental/

RUN cd ski-rental && mvn dependency:go-offline -B

COPY ski-rental/src ./ski-rental/src

RUN cd ski-rental && mvn clean package -DskipTests -B

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S javauser && adduser -S javauser -G javauser

COPY --from=builder /build/ski-rental/target/*.jar app.jar

RUN chown javauser:javauser /app/app.jar

USER javauser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]