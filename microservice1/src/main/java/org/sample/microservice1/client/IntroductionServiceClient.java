package org.sample.microservice1.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Maciej Kujawski <maciej.kujawski@seamless.se>
 */
@Path("/say")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IntroductionServiceClient
{
	@GET
	@Path("/hello")
	Hello sayHello();
}
