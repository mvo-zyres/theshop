FROM openjdk:11.0.12-jdk
RUN mkdir /app
workdir /app
COPY . /app
RUN ./gradlew build -x:test
ENTRYPOINT ["java", "-jar","/app/build/libs/TheShop-0.0.1-SNAPSHOT.jar"]
