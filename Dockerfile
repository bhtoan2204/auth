FROM gradle:8-jdk17 AS build
WORKDIR /app

# Copy build files first for better layer caching
COPY build.gradle settings.gradle ./

# Download dependencies (this layer will be cached if build.gradle doesn't change)
RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.gradle/wrapper \
    gradle dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Build application (using cache for dependencies)
RUN --mount=type=cache,target=/root/.gradle/caches \
    --mount=type=cache,target=/root/.gradle/wrapper \
    gradle build -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]