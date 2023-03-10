# Estate-Server

This is the server for the Estate project. It is a RESTful API that is used to store and retrieve data from the Estate database.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

- Get the git repository with the following command :

    ```bash
    git clone https://github.com/ThomasBTR/estate-server.git
    ```


### Prerequisites

What things you need to install the software and how to install them

You will need [maven](https://maven.apache.org/install.html) to build the project

This project run on Java 17, you can download it [here](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)

### Installing

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

Configured endpoints are definied in the application.yaml as follows :

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

## Contributing

ThomasBTR
