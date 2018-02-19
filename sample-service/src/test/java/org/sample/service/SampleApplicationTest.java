package org.sample.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.service.endpoint.Hello;
import org.sample.service.endpoint.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleApplicationTest
{
	@Autowired
	private SampleService sampleService;

	@Test
	public void testSayHello()
	{
		Hello hello = sampleService.sayHello();
		Assert.assertNotNull(hello);
		Assert.assertEquals("Spring Cloud with CXF, Hello World!", hello.getMessage());
	}
}
