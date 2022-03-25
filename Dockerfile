FROM gradle:jdk11

RUN ["mkdir", "-p", "/app"]
WORKDIR /app

COPY . /app/

EXPOSE 8080

CMD ["gradle", "bootRun"]
