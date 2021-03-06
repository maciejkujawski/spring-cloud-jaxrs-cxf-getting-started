package org.sample.microservice1.endpoint;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.ext.logging.Logging;
import org.sample.microservice1.client.Hello;
import org.sample.microservice1.client.IntroductionServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author Maciej Kujawski
 */
@RefreshScope
@Logging
@Service
@Path("/proxy")
public class ProxyService
{
	@Autowired
	private IntroductionServiceClient introductionServiceClient;

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Hello proxyHello()
	{
		return introductionServiceClient.sayHello();
	}
}
