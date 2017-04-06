FROM frolvlad/alpine-oraclejdk8:full
#FROM anapsix/alpine-java
VOLUME /tmp

ADD target/bookstore-1.0.0-SNAPSHOT.jar app.jar

RUN sh -c 'touch /app.jar'

CMD java -Dspring.couchbase.bootstrap-hosts=$HOSTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
