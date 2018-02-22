# Run

Before Running this run Discovery Service and Config Server and microservice1.

From IDE run: `org.sample.microservice1.Microservice1Application.main`

From terminal run:

`mvn spring-boot:run`

Check your service is available in Eureka: http://localhost:8761/ and http://localhost:8761/eureka/apps - second link is what other microservices can see - 
it may take up to 30 seconds it's available. 

Call proxy endpoint: http://localhost:8081/services/proxy/hello

# Spring Cloud Dependencies

The basic version of pom.xml for each microservice may look like that:

    <parent>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-parent</artifactId>
    	<version>1.5.10.RELEASE</version>
    	<relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <dependencies>
    	<dependency>
    		<groupId>org.springframework.cloud</groupId>
    		<artifactId>spring-cloud-starter-config</artifactId>
    	</dependency>
    	<dependency>
        	<groupId>org.springframework.cloud</groupId>
        	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
    
    <dependencyManagement>
    	<dependencies>
    		<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-dependencies</artifactId>
    			<version>Edgware.SR1</version>
    			<type>pom</type>
    			<scope>import</scope>
    		</dependency>
    	</dependencies>
    </dependencyManagement>
 	
 * parent pom is Spring Boot: `spring-boot-starter-parent`
 * dependency management is managed by: `spring-cloud-dependencies`
 * we have two dependencies: `spring-cloud-starter-config` - enables Spring Cloud Configuration features and `spring-cloud-starter-netflix-eureka-client` - enables Service Discovery using Eureka
  
## Enabling CXF 

Enabling JAX-RS CXF dependencies is quite easy thanks to:

    <dependency>
		<groupId>org.apache.cxf</groupId>
		<artifactId>cxf-spring-boot-starter-jaxrs</artifactId>
		<version>${cxf.version}</version>
	</dependency>
	
Having your REST API you will probably communicate using JSON so you need to add:
 
    <dependency>
		<groupId>com.fasterxml.jackson.jaxrs</groupId>
		<artifactId>jackson-jaxrs-json-provider</artifactId>
	</dependency>
 
There are also 2 dependencies that are not mandatory but probably you will want them: 

 * `cxf-rt-rs-service-description` adds ?_wadl endpoint which serves xml description of you REST endpoints: useful to check "what's there?" without checking the git repository. 
 * `cxf-rt-features-logging` - for enabling logging of requests and responses of your services with @Logging annotation. Since CXF 3.2 moved to separate maven dependency
 

Enabling JAX-RS causes by default some dependency problems with javax.ws.rs dependencies - it's common for CXF and Jersay but it seems Spring keep silent on that and ignores requested bugs... Probably it's not danger but it cause you need to exclude some dependencies from Spring Cloud... Actually the root of the problem is in Spring Boot so thanks to Spring Cloud is based on Spring Boot we have here the same problem. And the solution is ugly like that:

    <dependency>
	    <groupId>org.springframework.cloud</groupId>
		 <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		 <exclusions>
		 <!-- conflicts with jaxrs 2.0 from cxf causing runtime problems: method not found and some nasty logs on error level when requesting not mapped path -->
			<exclusion>
				<groupId>javax.ws.rs</groupId>
				<artifactId>jsr311-api</artifactId>
			</exclusion>
		</exclusions>
	</dependency>

# bootstrap.yml

Not much to say here. Setting name of our microservice - the most important and setting Config Server discovered thanks to our Service Discovery:

    spring:
      application:
        name: microservice1
      cloud:
        config:
          enabled: true
          discovery:
            enabled: true
            serviceId: CONFIG-SERVER
            
# application.yml

This microservice will be served by default on port 8081 and Spring MVC context will be served on servlet-path: `/`. Spring MVC serves nice out of the box 
management api like /health and /refresh which are useful in microservices architecture (thanks to dependency: `spring-boot-starter-actuator`).
CXF REST endpoints will be served under `/services` which is out of the box value for `cxf-spring-boot-starter-jaxrs`. 

    server:
      port: 8081
      
By default management api is secured using OAuth to disable this for demo purpose we have configuration:

    management:
      security:
        enabled: false
      
Setting client to Service Discovery is done by:

    eureka:
      client:
        serviceUrl:
          defaultZone: http://${eureka.host:localhost}:${eureka.port:8761}/eureka/
          
We are just pointing the url to Service Discovery (Eureka).

The configuration of cxf is:

    cxf:
      jaxrs:
        component-scan: true
        
Which says that JAX-RS services are served under `/services` (default value of `cxf.path` property). `component-scan: true` tells that all Spring `@Component`
 or `@Service` annotated classes with JAX-RS annotation `@Path` will be automatically exposed as JAX-RS Services.  

# Feign Client

Communication between microservice1 and microservice2 is accomplished using Feign Client. [Feign Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html) use Service Discovery and [Ribon: Client Side Load 
Balancer](https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-ribbon.html)
 to successfully call other microservice. Feign Client hides boilerplate code of creating REST implementations of clients. In our case we will create only 
 interface with JAX-RS annotations which will define REST client:
 
    @Path("/say")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @FeignClient(path="/services", name = "MICROSERVICE2")
    public interface IntroductionServiceClient
    {
    	@GET
    	@Path("/hello")
    	Hello sayHello();
    }
    
You can see that we have annotation `@FeignClient` which will create automatically Spring Bean which will be in fact JAX-RS Client to our service 
`microservice2`. It will automatically fetch available instance of `MICROSERVICE2` from Service Discovery. If there are more instances of desired service 
Ribon load balancer will do the job choosing the right one (by default simple round robin algorithm is used for that).
 
To enable creation of Feign Clients we need to annotate our application with `@EnableFeignClients`. In order to turn on JAX-RS annotation support for Feign 
Clients we have to create beans:

    /**
     * Support of JAX-RS annotations for creating Feign Clients
     *
     * @return
     */
    @Bean
    public Contract feignContract()
    {
    	return new JAXRSContract();
    }
    
    /**
     * Serialization of objects using default object mapper
     *
     * @param objectMapper
     * @return
     */
    @Bean
    public Encoder feignEncoder(ObjectMapper objectMapper)
    {
    	return new JacksonEncoder(objectMapper);
    }
    
    /**
     * Deserialization of objects using default object mapper
     *
     * @param objectMapper
     * @return
     */
    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper)
    {
    	return new JacksonDecoder(objectMapper);
    }

## pom.xml dependencies

    <!-- Feign Client -->
    <dependency>
    	<groupId>org.springframework.cloud</groupId>
    	<artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <!-- enable JAX-RS annotations for building Feign clients -->
    <dependency>
    	<groupId>io.github.openfeign</groupId>
    	<artifactId>feign-jaxrs</artifactId>
    	<exclusions>
    		<!-- conflicts with jaxrs 2.1 from cxf causing runtime problems: method not found and some nasty logs on error level when requesting not mapped path -->
    		<exclusion>
    			<groupId>javax.ws.rs</groupId>
    			<artifactId>jsr311-api</artifactId>
    		</exclusion>
    	</exclusions>
    </dependency>
    <dependency>
    	<groupId>io.github.openfeign</groupId>
    	<artifactId>feign-jackson</artifactId>
    </dependency>

# Advanced: changing context path of cxf services

By default JAX-RS CXF services will be served under `/services`. If you want to change that to main path `/` you will cause Spring 
Actuator endpoints stop working. So to solve it we need to move Spring Actuator to different context path, eg.: `/admin` and tell Eureka where is health 
endpoint. 

    server:
      port: 8081
      servlet-path: /admin
      
    cxf:
      path: /
      jaxrs:
        component-scan: true
        
    eureka:
      client:
        serviceUrl:
          defaultZone: http://${eureka.host:localhost}:${eureka.port:8761}/eureka/
      instance:
        # default is /info and /health but our management api is under /admin
        statusPageUrlPath: /admin/info
        healthCheckUrlPath: /admin/health
