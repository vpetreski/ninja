FROM gradle:8.4-jdk17 as BUILDER

COPY . /app
WORKDIR /app

RUN gradle --no-daemon clean build -x test

FROM eclipse-temurin:17

COPY --from=BUILDER /app/build/libs/ninja.jar app.jar

EXPOSE 80:80

ENTRYPOINT ["java", "--add-opens=java.base/java.net=ALL-UNNAMED", "-jar", "app.jar"]