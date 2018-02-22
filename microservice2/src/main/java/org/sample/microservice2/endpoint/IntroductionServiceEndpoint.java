package org.sample.microservice2.endpoint;


import org.apache.cxf.ext.logging.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author Maciej Kujawski
 */
@RefreshScope
@Logging
@Service
public class IntroductionServiceEndpoint implements IntroductionService
{
	@Value("${introduction.message:hello}")
	private String introductionMessage;

	@Override
	public Hello sayHello()
	{
		return new Hello(introductionMessage);
	}
}
