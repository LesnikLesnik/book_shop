FROM openjdk:17-jdk-slim
EXPOSE 8761
COPY target/eureka-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "eureka-0.0.1-SNAPSHOT.jar"]