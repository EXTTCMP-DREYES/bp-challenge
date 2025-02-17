FROM gradle:jdk17-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle gradle

RUN gradle build --no-daemon

COPY src/ src/

RUN gradle --no-daemon assemble

FROM amazoncorretto:17.0.14-alpine3.21

WORKDIR /app

COPY --from=build /app/build/libs/*0.0.1.jar app.jar

EXPOSE 80

CMD ["java", "-jar", "/app/app.jar"]
