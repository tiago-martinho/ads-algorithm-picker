FROM openjdk:13-alpine
EXPOSE 8080

WORKDIR /app
COPY target/*.jar ./ads-server.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "ads-server.jar"]