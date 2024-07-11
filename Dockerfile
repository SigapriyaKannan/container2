FROM maven:3.8.1-openjdk-17 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY target/container2-0.0.1-SNAPSHOT.jar container2.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/container2.jar"]
