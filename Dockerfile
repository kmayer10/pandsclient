FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift
ARG SERVICE_JAR_FILE
RUN echo ${SERVICE_JAR_FILE}
ADD ${SERVICE_JAR_FILE} pandsclient.jar
EXPOSE 54055
ENV SPRING.PROFILES.ACTIVE acc
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING.PROFILES.ACTIVE}",\
             "-Dspring.config.location=classpath:application.yml",\
             "-jar","pandsclient.jar"]