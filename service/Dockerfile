FROM gradle:jdk11 as builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle clean build -x test --stacktrace

RUN echo HELLO
RUN echo $(ls -l /home/gradle/src/build/libs)

FROM adoptopenjdk/openjdk11:latest as finalApp

ENTRYPOINT ["sudo", "mkdir", "/app/"]

WORKDIR app

#COPY --from=builder /home/gradle/src/build/libs/service-0.0.1-SNAPSHOT.jar /app

COPY --from=builder /home/gradle/src/build/libs/src-0.0.1-SNAPSHOT.jar /app/service-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/service-0.0.1-SNAPSHOT.jar"]
