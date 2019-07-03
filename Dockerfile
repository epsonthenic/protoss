FROM java:8
VOLUME /tmp
# ARG JAR_FILE
ADD target/lineBotRestaurant-0.0.1-SNAPSHOT.war target/app.war
EXPOSE 8080
RUN bash -c 'touch /app.war'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/app.war"]