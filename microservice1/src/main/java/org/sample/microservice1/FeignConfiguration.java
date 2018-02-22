package org.sample.microservice1;

import org.springframework.context.annotation.Bean;

import feign.Contract;
import feign.jaxrs.JAXRSContract;

/**
 * @author Maciej Kujawski
 */
public class FeignConfiguration
{
	@Bean
	public Contract feignContract()
	{
		return new JAXRSContract();
	}
}