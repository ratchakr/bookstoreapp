FROM frolvlad/alpine-oraclejdk8:full
#FROM anapsix/alpine-java
VOLUME /tmp
ADD target/bookstore-1.0.0-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
RUN apk update && apk add curl

ADD run_app.sh .
RUN chmod +x run_app.sh
CMD sh run_app.sh
