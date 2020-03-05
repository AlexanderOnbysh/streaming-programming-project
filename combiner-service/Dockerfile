FROM openjdk:alpine
EXPOSE 8080
COPY classes/twitter-service/combiner-service.jar /usr/src/app/
WORKDIR /usr/src/app
CMD java -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar combiner-service.jar