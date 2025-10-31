# ============
# 🏗️ STAGE 1 - BUILD THE APP
# ============
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /app

# Maven runner
COPY mvnw .

COPY .mvn .mvn

# Dependency 
COPY pom.xml .

COPY user_client/pom.xml user_client/pom.xml

COPY user_server/pom.xml user_server/pom.xml

COPY settings.xml .

RUN --mount=type=secret,id=github-username,env=GITHUB_USERNAME,required=true \
  --mount=type=secret,id=github-token,env=GITHUB_TOKEN,required=true \
  --mount=type=cache,target=/root/.m2 \
  cp ./settings.xml /root/.m2 && \
  ./mvnw dependency:go-offline -U

# Copy the full source code
COPY user_server/src user_server/src

COPY user_client/src user_client/src

RUN ./mvnw clean package -DskipTests

# ============
# 🚀 STAGE 2 - RUN THE APP
# ============
FROM eclipse-temurin:21-jre-alpine AS runner

# Add a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/user_server/target/*.jar app.jar

# Expose the default Spring Boot port (you can override in compose)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","/app/app.jar"]
