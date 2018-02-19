package org.sample.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
public class SampleApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(SampleApplication.class, args);
	}
}
