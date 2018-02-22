# Dependencies

pom.xml:

    <parent>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-parent</artifactId>
    	<version>1.5.10.RELEASE</version>
    	<relativePath/> <!-- lookup parent from repository -->
    </parent>
    
    <dependencies>
    	<dependency>
    		<groupId>org.springframework.cloud</groupId>
    		<artifactId>spring-cloud-starter-eureka-server</artifactId>
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
 * we have one dependency: `spring-cloud-starter-eureka-server` - enables Spring Cloud Configuration Server features
  
# application.yml

    eureka:
      instance:
        hostname: localhost
      client:
        registerWithEureka: false
        fetchRegistry: false
        serviceUrl:
          defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
        
That's Spring Cloud Configuration Server so we don't want it to register in other Eureka server (`registerWithEureka: false`) and we don't need to fetch other
 microservices urls (`fetchRegistry: false`)
 
# ServiceDiscoveryApplication

Main class of this server is simple as that:

    @SpringBootApplication
    @EnableEurekaServer
    public class ServiceDiscoveryApplication
    {
    	public static void main(String[] args)
    	{
    		SpringApplication.run(ServiceDiscoveryApplication.class, args);
    	}
    }
    
Where ` @EnableEurekaServer` do the magic.

# Advanced settings

When you will start to asking questions: `why my services are registering so slow in Eureka?` `why I can still see some service instance in Eureka even if I 
killed my application?` you should probably read more about internals of Eureka: https://blog.asarkar.org/technical/netflix-eureka/

Below you can find also copy and paste one of responses from: https://github.com/spring-cloud/spring-cloud-netflix/issues/373 which was useful for me to 
understand the basics (I couldn't find good answers for settings like: leaseRenewalIntervalInSeconds, registryFetchIntervalSeconds in Spring Docs...).

Thank you https://github.com/brenuart for explanation:

> (1) Client Registration
When using the default configuration, registration happens at the first heartbeat sent to the server. Since the client just started, the server doesn't know anything about it and replies with a 404 forcing the client to register. The client then immediately issues a second call with all the registration information. The client is now registered.

> The first heartbeat happens 30 seconds after startup (eureka.instance.leaseRenewalIntervalInSeconds) - so your instance won't appear in the Eureka registry before this interval.

> (2) Server ResponseCache
The server maintains a response cache that is updated every 30s by default (eureka.server.response-cache-update-interval-ms). So even if your instance is just registered, it won't appear in the result of a call to the /eureka/apps REST endpoint.

> However, your instance may appear in the UI web interface just after registration. This is because the web front-end bypasses the response cache used by the REST API...

> If you know the instanceId, you can still get some details from Eureka about it by calling /eureka/apps/<appName>/<instanceId>. This endpoint doesn't make use of the response cache. But since it requires to know the instance, it is of no help in the discovery process...

> So, it may take up to another 30s for other clients to discover your newly registered instance.

> (3) Client cache refresh
Eureka client maintain a cache of the registry information. This cache is refreshed every 30 seconds by default ('eureka.client.registryFetchIntervalSeconds`). So again, it may take another 30s before a client decides to refresh its local cache and discover newly registered instances.

> (4) LoadBalancer refresh
The load balancer used by Ribbon gets its information from the local Eureka client. It also maintains a local cache to avoid calling the discovery client for every request. This cache is refreshed every 30s (ribbon.serverListRefreshInterval). So again, it may take another 30s before your Ribbon client can make use of the newly registered instance.

> Note: this local cache is apparently required only to reduce the cost of obtaining server information from the used ServerList. This is not the case with none of the server list provided by default: DiscoveryEnabledNIWSServerList with Eureka, ConfigurationBasedServerList without.

> At the end, if you are not lucky, it may take up to 2 minutes before your newly registered instance starts receiving trafic from other clients.