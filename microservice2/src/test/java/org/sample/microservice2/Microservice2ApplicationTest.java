package org.sample.microservice2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.microservice2.endpoint.Hello;
import org.sample.microservice2.endpoint.IntroductionServiceEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Maciej Kujawski
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class Microservice2ApplicationTest
{
	@Autowired
	private IntroductionServiceEndpoint introductionService;

	@Test
	public void testSayHello()
	{
		Hello hello = introductionService.sayHello();
		Assert.assertNotNull(hello);
		Assert.assertEquals("hello", hello.getMessage());
	}
}
