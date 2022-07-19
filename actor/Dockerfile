FROM redhat/ubi8
RUN yum -y install java-1.8.0-openjdk
COPY build/libs/actor-1.0-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]