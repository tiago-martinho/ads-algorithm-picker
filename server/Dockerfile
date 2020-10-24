FROM maven:3.6.3-openjdk-11-slim AS MAVEN_BUILD
EXPOSE 8080
WORKDIR /build
COPY pom.xml ./
COPY src ./src/
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar ./ads-server.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=docker", "-jar", "ads-server.jar"]
