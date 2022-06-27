# TheShop
## Content
- 1.0)  What is this project?
- 2.0)  How to start the projects...
  - 2.1)  ... full version?
  - 2.2)  ... development version? 

---

## 1.0) What is this project?
This project is a spring boot 2.5.1 Java 1.11 Gradle project. It is being developed by Johannes Schrage, Marian Vonsien and Max Dudin, starting on March 1st, 2022, and the aim of developing a good/usable program to present in school.
The application is an online shop called 'TheShop', which comes from the book Qualityland by Mark-Uwe-Kling.

---
## 2.0)  How to start this project?
To go further, make sure you have the latest version of the project to avoid bugs and to get the most pleasant user experience possible.

**Important**: The following steps are only possible if you have a linux command line!!!
### 2.1) Start the full version
To start with the full version you have to copy the default container volume into the data folder.
To do this you need to open a command line, navigate into the projects folder and use the `sudo cp -a ./docker/template/. ./docker/data` command.
After you have done this you can just start the project with `docker-compose up` and wait until the message `Application started at: ...`  appears.
Then the project is started and ready to use on [https://localhost:8443](https://localhost:8443).

---
### 2.2) Start the development version (IntelliJ or command line)
To start with the development version you also have to copy the default container volume into the data folder.
To do this you need to open a command line, navigate into the projects folder and use the `cp -R ./docker/template ./docker/data` command.
After you have done this you can navigate into the `./development` folder and you can start the external tools with `docker-compose up`.
When selected the bootRun in the run configurations, edit the bootRun configuration and copy this:
```properties
THESHOP_DEV=true;
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/theshop;
SPRING_DATASOURCE_USERNAME=theshop;
SPRING_DATASOURCE_PASSWORD=12345678;
THESHOP_TYPESENSE_URL=http://localhost:8108;
THESHOP_MINIO_INNERHOSTNAME=localhost;
```
into the `Environment variables` field.
If you have done this step you can start the project by pressing the play button/ run button on the top right of the IDE.
If you want to run this project from the command line you can just run this command in the main project directory: 
```
THESHOP_DEV=true SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/theshop SPRING_DATASOURCE_USERNAME=theshop SPRING_DATASOURCE_PASSWORD=12345678 THESHOP_TYPESENSE_URL=http://localhost:8108 THESHOP_MINIO_INNERHOSTNAME=localhost ./gradlew bootRun
```

Then the project is started and ready to use on [https://localhost:8443](https://localhost:8443).

---
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>
<br><br>

#### Environment variables with bootRun
```properties
THESHOP_DEV=true;
SPRING_DATASOURCE_PASSWORD=12345678;
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/theshop;
SPRING_DATASOURCE_USERNAME=theshop;
THESHOP_TYPESENSE_URL=http://localhost:8108;
THESHOP_MINIO_INNERHOSTNAME=localhost;
```
#### Docker compose command
```shell
docker-compose up -d
```
#### Docker compose command for development
```shell
docker-compose up -d theshop_development
```
#### Postgres DB Container
```shell
docker run --rm -dp 5432:5432 --name theshop4-0_databse -e POSTGRES_USER=theshop -e POSTGRES_PASSWORD=12345678 postgres:13-alpine
```
#### Minio S3 Container
```shell
docker run --rm -d -p 9001:9001 -p 9000:9000 -e MINIO_ROOT_USER=theshop -e MINIO_ROOT_PASSWORD=12345678 minio/minio server --console-address :9001 /data
```
#### TypeSense Container
 ```shell
docker run --rm -dp 8108:8108 -v /tmp/typesense-data:/data typesense/typesense:0.21.0 \ --data-dir /data --api-key=12345678
```
#### PGAdmin4 Container
```shell
docker run --rm -dp 666:80 -e PGADMIN_DEFAULT_EMAIL=theshop@theshop.com -e PGADMIN_DEFAULT_PASSWORD=12345678 dpage/pgadmin4
```
#### bootBuildImage run configuration
```shell
./gradlew bootBuildImage --imageName=mv0zyres/theshop1:latest
```

#### Docker login
```shell
docker login
```
#### Copy default minio volume to minio container volume
```shell
cp -R ./docker/template ./docker/data
```
