FROM openjdk:8u191-jdk-alpine3.9
ADD target/orders-1.0.0.jar .
EXPOSE 8082
CMD java -jar orders-1.0.0.jar