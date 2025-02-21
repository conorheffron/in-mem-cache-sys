FROM eclipse-temurin:22-jdk
#FROM anapsix/alpine-java
MAINTAINER conorheffron
COPY target/app-1.0-SNAPSHOT.jar /home/app.jar
CMD ["java","-jar","/home/app.jar"]
