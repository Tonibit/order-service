FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY build/libs/OrderService-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]