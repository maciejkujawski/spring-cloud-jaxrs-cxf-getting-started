package org.sample.microservice1;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

/**
 * You need application/json message body provider otherwise you will get response:
 * `No message body writer has been found for class org.sample.microservice1.client.Hello, ContentType: application/json`
 *
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@Configuration
public class Config
{
	@Bean
	@ConditionalOnMissingBean
	public JacksonJsonProvider jsonProvider(ObjectMapper objectMapper)
	{
		JacksonJsonProvider provider = new JacksonJsonProvider();
		return provider;
	}
}
