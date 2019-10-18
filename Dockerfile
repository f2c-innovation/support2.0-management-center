FROM registry.fit2cloud.com/fit2cloud2/fabric8-java-alpine-openjdk8-jre

MAINTAINER FIT2CLOUD <guohao.ma@fit2cloud.com>

RUN mkdir -p /opt/apps

ADD target/support2.0-management-center-2.0.jar /opt/apps

ENV JAVA_APP_JAR=/opt/apps/support2.0-management-center-2.0.jar

ENV AB_OFF=true

ENV JAVA_OPTIONS=-Dfile.encoding=utf-8

EXPOSE 8088

HEALTHCHECK --interval=15s --timeout=5s --retries=20 --start-period=30s CMD curl -f 127.0.0.1:6602

CMD ["/deployments/run-java.sh"]

