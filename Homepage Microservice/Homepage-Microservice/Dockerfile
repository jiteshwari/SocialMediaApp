# Use a lightweight base image with JDK 17
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the pre-built JAR file from the local machine into the container
COPY target/Homepage-Microservice-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application will run on (adjust if needed)
EXPOSE 8083

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
