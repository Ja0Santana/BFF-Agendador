FROM gradle:8.7-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/bff-agendador.jar

EXPOSE 8083

CMD ["java", "-jar", "/app/bff-agendador.jar"]