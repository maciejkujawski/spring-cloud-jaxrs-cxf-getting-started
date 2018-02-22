package org.sample.microservice1.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author Maciej Kujawski
 */
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
