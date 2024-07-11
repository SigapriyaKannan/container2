FROM maven:3.8.1-openjdk-11 AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:17
COPY target/container2-0.0.1-SNAPSHOT.jar container2.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/container2.jar"]
