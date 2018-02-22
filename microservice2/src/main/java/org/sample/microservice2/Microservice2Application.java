package org.sample.microservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Maciej Kujawski
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class Microservice2Application
{
	public static void main(String[] args)
	{
		SpringApplication.run(Microservice2Application.class, args);
	}
}
