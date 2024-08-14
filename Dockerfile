FROM openjdk:21-jdk-slim

 # Copy the JAR file from your local machine to the container
 COPY out/artifacts/Spring_2/Spring_2.jar /Spring_2.jar

 # Set the entry point to run the JAR file
 ENTRYPOINT ["java", "-jar", "/Spring_2.jar"]