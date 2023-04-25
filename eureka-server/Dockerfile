# Create jar package
FROM maven:3.8-openjdk-17 as maven
WORKDIR /app
COPY . /app
RUN mvn clean package

# Launch the app
FROM openjdk:17.0.2-jdk
WORKDIR /app
COPY --from=maven /app/eureka-server/target/eureka-server-1.0-SNAPSHOT.jar app.jar
CMD java -jar app.jar