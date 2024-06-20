FROM openjdk:17-jdk-slim
EXPOSE 8989
COPY target/gateway-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]