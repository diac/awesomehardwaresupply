# Create jar package
FROM maven:3.8-openjdk-17 as build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 mvn -f $HOME/pom.xml --projects domain,authentication clean package -Dmaven.test.skip=true

# Launch the app
FROM openjdk:17.0.2-jdk
COPY --from=build /usr/app/authentication/target/authentication-0.0.1-SNAPSHOT.jar /app/app.jar
CMD java -jar /app/app.jar