FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY eureka-server/build.gradle.kts eureka-server/
COPY eureka-server/src eureka-server/src

RUN ./gradlew eureka-server:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
RUN apk add --no-cache wget
VOLUME /tmp
COPY --from=build /workspace/app/eureka-server/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
