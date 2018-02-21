# Introduction

* Clones repository https://github.com/maciejkujawski/spring-cloud-jaxrs-cxf-getting-started.git on startup and watches for changes.
* Serves configs for microservices

# Dependencies

It's standard Spring Boot application with parent pom: `spring-boot-starter-parent` with Spring Cloud so we have:

    <dependencyManagement>
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-dependencies</artifactId>
    			<version>${spring-cloud.version}</version>
    			<type>pom</type>
    			<scope>import</scope>
    		</dependency>
    	</dependencies>
    </dependencyManagement>

Additionally we want to have Spring Config Server functionality here so we have to add:

    <dependency>
    	<groupId>org.springframework.cloud</groupId>
    	<artifactId>spring-cloud-config-server</artifactId>
    </dependency>

Additionally we want our Config Server to register in Service Discovery so we need to add:

    <dependency>
    	<groupId>org.springframework.cloud</groupId>
    	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    
# bootstrap.yml

Not much to say here. Setting name of our microservice.

    spring:
      application:
        name: config-server

# application.yml

Setting client to Service Discovery is done by:

    eureka:
      client:
        serviceUrl:
          defaultZone: http://${eureka.host:localhost}:${eureka.port:8761}/eureka/
          
We are just pointing the url to Service Discovery (Eureka).

We are configuring git repository from where configuration of our microservices is fetched from by:

    spring:
      cloud:
        config:
          server:
            git:
              uri: https://github.com/maciejkujawski/spring-cloud-jaxrs-cxf-getting-started.git
              searchPaths: ['/config/','/config/{application}']
              clone-on-start: true
              

This git repository has public access so you don't need any credentials to clone it. If you want to play with yours settings you can change it to sth like 
`file:///Users/your_usr/Documents/cloud/config` and just make sure you have git repository in that folder (`git init`) or clone there yours repository.

Parameter `searchPaths` tells where in cloned repository search for configuration files (.properties, .yml). This configuration tells that files are in 
/config folder and also they can be found under /config/{application}, where `{application}` means application name, 
ex: sample-service (application name is defined in bootstrap.yml: `spring.application.name`).

Parameter `clone-on-start` cause repository is cloned to tmp directory on every startup.

> Setting a repository to be cloned when the Config Server starts up can help to identify a misconfigured configuration source (e.g., an invalid repository URI) quickly, while the Config Server is starting up. With cloneOnStart not enabled for a configuration source, the Config Server may start successfully with a misconfigured or invalid configuration source and not detect an error until an application requests configuration from that configuration source.

More information can be found at: http://cloud.spring.io/spring-cloud-static/spring-cloud-config/1.4.2.RELEASE/single/spring-cloud-config.html

# Run

Before Running this run Discovery Service.

From IDE run: `org.sample.config.server.ConfigServerApplication.main`

From terminal run:

`mvn spring-boot:run`

Check configuration is available: http://localhost:8888/sample-service/default for `sample-service`

Check your service is available in Eureka: http://localhost:8761/