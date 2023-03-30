# Estate-Server

This is the server for the Estate project. It is a RESTful API that is used to store and retrieve data from the Estate database.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

- Get the git repository with the following command :

    ```bash
    git clone https://github.com/ThomasBTR/estate-server.git
    ```

- Go to the docker compose folder

    ```bash
    cd estate-server/docker
    ```  

- Run the following command to start the database and the minio server

    ```bash
    docker-compose up -d
    ```

- Go to the project folder

    ```bash
    cd estate-server
    ```
- Run the following command to install the project

    ```bash
    mvn clean install
    ```
- Run the following command to run the project

    ```bash
    mvn spring-boot:run
    ```
- The project is now running on http://localhost:3001
- The documentation is available on http://localhost:3001/swagger-ui/index.html
- The API documentation is available on http://localhost:3001/api/v1/docs
- The database is running on http://localhost:3306
- The minio server is running on http://localhost:9000

### Prerequisites

What things you need to install the software and how to install them

You will need for this project :

- [maven](https://maven.apache.org/install.html) to build the project
- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) to run the project
- [Docker](https://docs.docker.com/get-docker/) to run the database and the minio server

### The database and minio server

To simulate the database and the minio server, we will use docker.
Using a docker compose file, I set up the MySql database and a minio server to reproduce an object storage container.
The minio server can be replaced by an AWS S3 bucket.

#### What you will found

- In the ./docker/ folder, you will find :
  - the docker-compose.yml file that will run the database and the minio server.
  - the ./docker/.env file that contains the environment variables for the database and the minio server.

#### Environment variables

Everything is commented so you can easily understand what is going on.

- The environment variables are defined in the ./docker/.env file.
- The variables for spring are defined in the ./src/main/resources/application.yaml file.
- Estate peculiar variables are the following :

```yaml
...
  
...
```

#### Running and stopping the database and the minio server with docker compose

- Go to ./docker/, run the following command :

    ```bash
    docker-compose up -d
    ```

- To stop the database and the minio server, run the following command :

    ```bash
    docker-compose down
    ```

### Installing the project

A step by step series of examples that tell you how to get a development env running

- To quickly run all maven cycle on this project, run the following command :

    ```bash
    mvn clean install
    ```

- Run the following command to only package the project

    ```bash
    mvn clean package
    ```

## Getting started with the API

The documentation is based on the [spring doc](https://springdoc.org/v2/) specification.

Configured endpoints are defined in the application.yaml as follows :

```yaml
springdoc:
  api-docs:
    path: /api/v1/docs
  swagger-ui:
    path: /swagger-ui
```

### A fair warning :

***A specific endpoint is defined in configuration to get the documentation of the API out of the security scope.
</br>This is done only for demonstration purpose, in a real world application, the documentation should be secured.***

The documentation path is defined per se in spring-doc specification of
the [swagger-ui](https://springdoc.org/v2/#getting-started)
and [api-docs](https://springdoc.org/v2/#spring-webmvc-support) endpoints.

For example, if you run on your machine without changing the server port, the documentation will be available
here: http://localhost:3001/swagger-ui/index.html </br>
and you will get the API documentation from this link : http://localhost:3001/api/v1/docs

End with an example of getting some data out of the system or using it for a little demo

## Running the tests -currently not implemented

The only test implemented are unit tests for spring starting purpose.
Unit test are not on the scope of this project.

- Run the following command to only run the unit tests
    ```bash
    mvn clean test
    ```

### Break down into end-to-end tests -currently not implemented

End to end or integration tests are not on the scope of this project.

## Deployment -currently not implemented

Add additional notes about how to deploy this on a live system

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring](https://spring.io/) - The web framework used
* [Spring Security](https://spring.io/projects/spring-security) - Authentication and authorization framework
* [Spring Data](https://spring.io/projects/spring-data) - Data access framework
* [Spring Doc](https://springdoc.org/) - OpenAPI 3 and Swagger 2 generation for Spring REST APIs
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework to ease the bootstrapping and development of new
  Spring Applications
* [Minio](https://min.io/) - Open Source Object Storage

## Contributing

ThomasBTR

for linux or macOS users, if your project ist located at $HOME/estate-server, the value to set is :

```yaml
...
estate:
  file:
    storage-mapping: file:~/Desktop/estate-server/src/main/resources/static/images
```

for Windows users, if this project is located on the local disk C at C:/projects/estate-server, the value to set is :

```yaml
...
estate:
  file:
    storage-mapping: file:///D:/projects/estate-server/src/main/resources/static/images
```