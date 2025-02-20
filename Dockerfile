FROM eclipse-temurin:22-jdk

COPY target *.jar app.jar
RUN sh -c 'touch /app.jar'

RUN java -jar /app.jar
