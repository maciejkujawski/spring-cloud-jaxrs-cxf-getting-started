# Introduction

Here you can find "Hello World" project of [Spring Cloud](http://projects.spring.io/spring-cloud/) microservices based on JAX-RS [Apache CXF](http://cxf.apache.org/).

> Microservices - also known as the microservice architecture - is an architectural style that structures an application as a collection of loosely coupled services, which implement business capabilities. The microservice architecture enables the continuous delivery/deployment of large, complex applications. It also enables an organization to evolve its technology stack.

Check here: http://microservices.io/, https://www.nginx.com/blog/introduction-to-microservices/ for more information.

In real life building microservices will require some infrastructure where most probably two elements are crucial and without them microservices will not be real microservices:

* Service Discovery - enables registration and discovery of microservices which means that instead providing static configuration of where your services are available, microservices will automatically register and lookup other microservices to communicate with using Service Discovery. Examples here are using Eureka Server for this purpose. Check: https://www.nginx.com/blog/service-discovery-in-a-microservices-architecture/ for more information.
* Configuration Server - microservice architecture makes your product spread between dozens of microservices and moreover enables you to start several instances of given microservice. Having configuration served from filesystem would be a nightmare and would cause a lot of problems. Having Configuration Server helps to manage your configuration and enables you to change easily behaviour of your microservice (without releasing new version of component or deploying configuration to your servers). Moreover running microservices can fetch changes from Configuration Server without restart when using @RefreshScope annotation on your beans. Examples here are using Spring Cloud Config which serves configuration from single git repository and expose that configuration through REST API.

<pre>
               ----------------                       ----------------
              | microservice 1 | ------------------> | microservice 2 |
               ----------------                       ----------------



                    -------------------          ---------------    
                   | service-discovery |        | config-server |
                    -------------------          ---------------                       
</pre>

This example shows two microservices where first one is calling second one using Service Discovery and [Feign Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html) build on JAX-RS annotations. Microservice2 is executing it's logic based on config-server.

# Structure of repository

Every folder in repository holds separate project which in real life could be hold in separate git repository. I have structured this is such way cause it 
seems easier to clone one repository with documentation and all examples. There's no parent pom here. Root directory of this repository holds only this readme file.

## /config

Holds configuration of services in single git location. Changing configuration requires only push to master branch (default place from where configuration is pulled). Common configuration may be served in `application.properties/yml` file. Application specific configuration is kept in `{application_name}.properties/yml` files. So for example: `sample-service.properties` holds configuration of microservice called `sample-service`.

Running Spring microservice with some profile, ex: `production`, enables to configure your microservice for given environment only. You can have file: `sample-service-production.properties` which will override properties hold in `sample-service.properties` and `application.properties` when your microservice will be run with Spring's Profile: `production` and it's name is `sample-service`.

This repository is used by config-server to provide configuration for microservices. Check here for [more description](./config/README.md)

## /config-server

Implementation of Spring Boot Config Server. Configuration of Config Server points /config folder of https://github.com/maciejkujawski/spring-cloud-jaxrs-cxf-getting-started.git as a root directory where configuration of your microservices are kept. Check here for [more description](./config-server/)

## /service-discovery-registry

Service Discovery implementation based on Eureka. Check here for [more description](./service-discovery-registry/)

## /smicroservice1

Example of sample microservice based on Spring Cloud and CXF calling other microservice using [Feign Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html) and Service Discovery (Eureka). The most interesting part of that example. Check here for [more description](./microservice1/)

## /microservice2

Example of sample microservice based on Spring Cloud and CXF. The most interesting part of that example. Check here for [more description](./microservice2/)
