FROM openjdk:8
ADD target/ToDo-0.0.1-SNAPSHOT.jar ToDo.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "ToDo.jar"]