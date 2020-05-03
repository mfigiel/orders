FROM openjdk:8u191-jdk-alpine3.9
ADD target/orders-0.0.1-SNAPSHOT.jar .
EXPOSE 8082
CMD java -jar orders-0.0.1-SNAPSHOT.jar