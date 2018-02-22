package org.sample.microservice1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.microservice1.client.Hello;
import org.sample.microservice1.endpoint.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Microservice1ApplicationTest
{
	@Autowired
	private ProxyService proxyService;

	@Test
	public void testProxyHello()
	{
		Hello hello = proxyService.proxyHello();
		Assert.assertNotNull(hello);
		Assert.assertEquals("Spring Cloud with CXF, Hello World!", hello.getMessage());
	}
}
