package org.sample.microservice1;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.jaxrs.JAXRSContract;


@Configuration
public class Config
{
	/**
	 * We need application/json message body provider otherwise you will get response:
	 * `No message body writer has been found for class org.sample.microservice1.client.Hello, ContentType: application/json`
	 *
	 * @param objectMapper
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper)
	{
		JacksonJsonProvider provider = new JacksonJsonProvider(objectMapper);
		return provider;
	}

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
}
