# Stage 1: build the jar
FROM gradle:5.2-jdk11 as builder

ENV WORKING_DIRECTORY /home/gradle/app

RUN mkdir -p $WORKING_DIRECTORY

WORKDIR $WORKING_DIRECTORY

COPY build.gradle .
COPY src .

RUN gradle assemble

# Stage 2: copy the jar from previous stage

FROM openjdk:11

ENV WORKING_DIRECTORY /usr/src/app

WORKDIR $WORKING_DIRECTORY

COPY --from=builder /home/gradle/app/build/libs/app-1.0.0.jar app.jar

CMD [ "java", "-jar", "app.jar"]
