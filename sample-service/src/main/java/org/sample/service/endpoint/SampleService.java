package org.sample.service.endpoint;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/sample")
public interface SampleService
{
	@GET
	@Path("/hello")
	Hello sayHello();

}
