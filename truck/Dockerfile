FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY ./target/truck-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.jpa.show-sql=false","-jar","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=72m","/app.jar"]

