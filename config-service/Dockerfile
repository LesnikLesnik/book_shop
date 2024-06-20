FROM openjdk:17-jdk-slim
EXPOSE 8888
COPY target/config-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "config-service-0.0.1-SNAPSHOT.jar"]