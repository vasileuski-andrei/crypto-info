FROM openjdk:17-oracle
ENV ARTIFACT_NAME=crypto-service.jar
ARG JAR_FILE=build/libs/$ARTIFACT_NAME
COPY $JAR_FILE app.jar
ENTRYPOINT ["java","-jar","/app.jar"]