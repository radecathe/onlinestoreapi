FROM java:8-jdk-alpine
COPY ./onlinestoreapi-1.0.0.jar /usr/app/
WORKDIR /usr/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "onlinestoreapi-1.0.0.jar"]