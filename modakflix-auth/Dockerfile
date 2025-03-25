# Use a base image for Java 23 (if available). Check for updates or adapt if necessary.
FROM openjdk:23-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged JAR file to the container
COPY target/modakflix-auth-1.1.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
