FROM openjdk:8-jdk-alpine
MAINTAINER moviebokkingapp.in
COPY target/Movie-service.jar Movie-service.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","Movie-service.jar"]