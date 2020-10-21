FROM openjdk:15-alpine
EXPOSE 8080

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build

RUN mvn package

ARG JAR_FILE=/build/target/*.jar
COPY $JAR_FILE /app/ads-server.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "ads-server.jar"]
