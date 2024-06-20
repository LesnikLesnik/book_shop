FROM openjdk:17-jdk-slim
EXPOSE 9999
COPY target/notification-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]