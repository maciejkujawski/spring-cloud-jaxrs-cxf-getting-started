package org.sample.microservice2.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Maciej Kujawski
 */
@Path("/say")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface IntroductionService
{
	@GET
	@Path("/hello")
	Hello sayHello();
}
