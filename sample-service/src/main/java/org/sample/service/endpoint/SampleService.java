package org.sample.service.endpoint;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.ext.logging.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@RefreshScope
@Logging
@Service
@Path("/sample")
public class SampleService
{
	@Value("${common.property:default}")
	private String commonProperty;

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Hello sayHello()
	{
		Hello hello = new Hello();
		hello.setMessage("Spring Cloud with CXF, Hello World!");

		return hello;
	}
}
