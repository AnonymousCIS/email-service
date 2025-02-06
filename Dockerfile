FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/emailservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENV SPRING_PROFILES_ACTIVE=default,prod
ENV DB_HOST=localhost:1521
ENV DDL_AUTO=update
ENV JWT_VALID_TIME=900

ENV REDIS_PORT=6379

ENTRYPOINT ["java", "-jar", "-Ddb.host=${DB_HOST}", "-Ddb.password=${DB_PASSWORD}", "-Ddb.username=${DB_USERNAME}", "-Dddl.auto=${DDL_AUTO}", "-Dconfig.server=${CONFIG_SERVER}", "-Deureka.server=${EUREKA_SERVER}", "-Dhostname=${HOSTNAME}","-Dredis.host=${REDIS_HOST}", "-Dredis.port=${REDIS_PORT}", "-Dmail.username=${MAIL_USERNAME}", "-Dmail.password=${MAIL_PASSWORD}", "-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}", "app.jar"]

EXPOSE 3007