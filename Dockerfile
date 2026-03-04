# ---- Build Spring Boot ----
FROM gradle:jdk21-ubi AS backend-builder

WORKDIR /app

COPY gradlew gradlew
COPY gradle/ gradle/
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew || true

RUN --mount=type=cache,target=/home/gradle/.gradle \
  ./gradlew --no-daemon dependencies || true

COPY . .
RUN --mount=type=cache,target=/home/gradle/.gradle \
  ./gradlew --no-daemon bootJar -x test

# ---- Final Runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=backend-builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
