# spring-cloud-jaxrs-cxf-getting-started
That's example project that might be used as a starter for Spring Cloud microservices based on CXF. In Spring Cloud page (http://projects.spring.io/spring-cloud/) you can find some sample projects and documentation that helps to understand how to build microservices but in my opinion it's really hard to start new project based on those examples. There is number of outdated examples there is lot's of dependencies you will probably end up finally on production with but as a simple "Getting Started" example is definitelly useless. Some of those examples just does not work. There is lack of information about JAX-RS support (only few sentences...) but you can google some examples with Jersey implementation and Spring Cloud... but Spring Cloud and CXF is like a mistery... best you can find is Spring Boot and CXF.

# Introduction

This repository contains microservices examples running on Spring Boot and Spring Cloud. 

> Microservices - also known as the microservice architecture - is an architectural style that structures an application as a collection of loosely coupled services, which implement business capabilities. The microservice architecture enables the continuous delivery/deployment of large, complex applications. It also enables an organization to evolve its technology stack.

Check here: http://microservices.io/, https://www.nginx.com/blog/introduction-to-microservices/ if you are not sure, you are on good page but most probably if you don't know what microservice is those examples will be useless for you.

In real life building microservices will require some infrastructure where two elements are probably crucial and without them microservices will not be real microservices:

* Service Discovery - enables registration of microservices and real time discovery which means you do not provide static configuration of where your services are available. Those information are build during startup of you microservices and provided by Service Discovery. In examples here I am using Eureka Server for this purpose. For more information check: https://www.nginx.com/blog/service-discovery-in-a-microservices-architecture/
* Configuration Server - microservice architecture makes your product spread between dozens of microservices and moreover enables you to start several instances of given microservice. Having configuration served from filesystem would be a nightmare and would case a lot of problems. Having Configuration Server helps to manage your configuration and enables you to change behaviour of your microservices without changing a code. Examples here are using Spring Cloud Config which by default serves configuration from single git repository.

<pre>
               ----------------                       ----------------
              | microservice 1 | ------------------> | microservice 2 |
               ----------------                       ----------------



                    -------------------          ---------------    
                   | service-discovery |        | config-server |
                    -------------------          ---------------                       
</pre>

# Structure of repository

Every folder in repository holds seperate project which in real life could be hold in separete git repository. I have structured this is such way cause it seems easier to clone one repository with documentation and all examples. There's no parent pom here. Root directory of this repository holds only this readme file.

## /config

Holds configuration of services in single git location. Changing configuration requires only push to master branch (default place from where configuration is pulled). Common configuration may be served in `application.properties/yml` file. Application specific configuration is kept in `{application_name}.properties/yml` files. So for example: `sample-service.properties`.

Running Spring microservice with some profile, ex: `prd`, enables to configure your microservice for given environment only. You can have file: `sample-service-prd.properties` which will ovveride properties hold in `sample-service.properties` and `application.properties` when your microservice will be run with Spring's Profile: `prd`.

This repository is used by config-server to provide configuration for microservices.

## /config-server

Implementation of Spring Boot Config Server. Configuration of Config Server points /config folder of https://github.com/maciejkujawski/spring-cloud-jaxrs-cxf-getting-started.git as a root directory where configuration of your microservices are kept. 

## /service-discovery-registry

Service Discovery implementation based on Eureka

## /sample-service

Example of sample service based on Spring Cloud and CXF. The most interesting part of that example

## /sample-service-client

Example of microservice talking to sample-service
