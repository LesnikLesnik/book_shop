FROM openjdk:17-jdk-slim
EXPOSE 8082
COPY target/bill-service-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "bill-service-0.0.1-SNAPSHOT.jar"]