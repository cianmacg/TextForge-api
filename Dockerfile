# Stage 1: Build
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Define the arguments (these will be passed during 'docker build')
ARG GITHUB_USER
ARG GITHUB_TOKEN

# Export them as Environment Variables for the RUN command
ENV GPR_USER=${GITHUB_USER}
ENV GPR_TOKEN=${GITHUB_TOKEN}

COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew

COPY src src

# Gradle will use the ENV variables we just set
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime
FROM container-registry.oracle.com/java/openjdk:24
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "--enable-preview", "-jar", "app.jar"]