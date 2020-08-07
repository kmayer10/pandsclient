FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift
#ARG SERVICE_JAR_FILE
#RUN echo ${SERVICE_JAR_FILE}
ADD target/pandsclient.jar pandsclient.jar
EXPOSE 54045
ENTRYPOINT ["java","-Dspring.profiles.active=${SPRING.PROFILES.ACTIVE}",\
             "-Dspring.config.location=classpath:application.yml",\
             "-jar","pandsclient.jar"]
