package org.sample.microservice1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sample.microservice1.client.Hello;
import org.sample.microservice1.client.IntroductionServiceClient;
import org.sample.microservice1.endpoint.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maciej Kujawski
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Microservice1ApplicationTest
{
	@Autowired
	private ProxyService proxyService;

	// Mock IntroductionServiceClient otherwise Feign Client will try to build JAX-RS client based on Discovery Service
	// Alternatively you can run stub of microservice2 using Spring Cloud Contract and test real communication
	@MockBean
	private IntroductionServiceClient introductionServiceClient;

	@Before
	public void beforeTest()
	{
		// Mock response from IntroductionServiceClient
		Hello hello = new Hello();
		hello.setMessage("Spring Cloud with CXF, Hello World!");
		Mockito.when(introductionServiceClient.sayHello()).thenReturn(hello);
	}

	@Test
	public void testProxyHello()
	{
		Hello hello = proxyService.proxyHello();
		Assert.assertNotNull(hello);
		Assert.assertEquals("Spring Cloud with CXF, Hello World!", hello.getMessage());
	}
}
