FROM maven:3.6.3-openjdk-11 AS MAVEN_BUILD
EXPOSE 8080

COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build

RUN mvn package

FROM openjdk:11-jre
ARG JAR_FILE=/build/target/*.jar
COPY --from=MAVEN_BUILD $JAR_FILE /app/ads-server.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "ads-server.jar"]
