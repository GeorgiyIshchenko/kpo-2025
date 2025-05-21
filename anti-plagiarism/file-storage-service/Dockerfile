FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY gradlew .
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY file-storage-service/build.gradle.kts file-storage-service/
COPY file-storage-service/src file-storage-service/src

RUN ./gradlew file-storage-service:bootJar -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
VOLUME /file-storage
COPY --from=build /workspace/app/file-storage-service/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]