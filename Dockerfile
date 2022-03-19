FROM amazoncorretto:11.0.14

RUN ["mkdir", "-p", "/app"]
WORKDIR /app

COPY . /app/
RUN ["./gradlew", "build"]

EXPOSE 8080

CMD ["./gradlew", "bootRun"]
