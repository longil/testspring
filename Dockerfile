FROM openjdk:8-alpine
RUN mkdir app
COPY ./target/testspring.jar /app
CMD java -jar ./app/testspring.jar