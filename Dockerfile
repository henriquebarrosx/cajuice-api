FROM maven:3.9-eclipse-temurin-24-alpine AS base
WORKDIR /usr/src/app

FROM base AS install
COPY pom.xml .
RUN mvn verify -DskipTests -B --fail-at-end || true

FROM base AS prerelease
COPY --from=install /root/.m2 /root/.m2
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests -B

FROM eclipse-temurin:24-jre-alpine AS release
WORKDIR /app
COPY --from=prerelease /usr/src/app/target/*.jar ./app.jar
USER guest
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
