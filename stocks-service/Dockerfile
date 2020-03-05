FROM openjdk:alpine
EXPOSE 8080
COPY classes/artifacts/stocks_service_jar/stocks-service.jar /usr/src/app/
WORKDIR /usr/src/app
CMD java -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar stocks-service.jar