FROM amazoncorretto:11

WORKDIR /application

COPY * .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "quote.jar"]