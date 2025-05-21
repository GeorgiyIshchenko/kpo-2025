FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY file-analysis-service/build.gradle.kts file-analysis-service/
COPY file-analysis-service/src file-analysis-service/src

RUN ./gradlew file-analysis-service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/file-analysis-service/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]