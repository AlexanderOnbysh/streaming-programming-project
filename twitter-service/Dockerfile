FROM centos:centos7

ENV LANG en_US.UTF-8
ENV LC_ALL en_US.UTF-8

RUN yum update -y && \
    yum install -y sudo && \
    yum install -y java-1.8.0-openjdk-devel && \
    curl https://bintray.com/sbt/rpm/rpm | sudo tee /etc/yum.repos.d/bintray-sbt-rpm.repo && \
    sudo yum install -y sbt && \
    yum clean all

WORKDIR /project
COPY . /project
RUN sbt assembly

FROM openjdk:alpine
COPY --from=0 /project/service.jar /usr/src/app/
WORKDIR /usr/src/app
CMD java -XX:+PrintFlagsFinal $JAVA_OPTIONS -jar service.jar