FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY api-gateway/build.gradle.kts api-gateway/
COPY api-gateway/src api-gateway/src

RUN ./gradlew api-gateway:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/api-gateway/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]