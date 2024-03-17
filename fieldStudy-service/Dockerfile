FROM maven:3.9.6 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests=true


FROM eclipse-temurin:17.0.8.1_1-jdk-focal

WORKDIR /app

COPY --from=build /app/target/studentManagement-1.jar studentApp.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "studentApp.jar"]
