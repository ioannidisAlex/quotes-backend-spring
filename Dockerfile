FROM maven:3.8.4-openjdk-17 AS build


WORKDIR /codes
COPY pom.xml .
RUN mvn dependency:resolve

COPY src /codes/src
RUN mvn verify package

FROM amazoncorretto:17

WORKDIR /app
COPY --from=build /codes/target/quote-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]