package org.sample.service.endpoint;


import org.apache.cxf.ext.logging.Logging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author Maciej Kujawski <kujawski.maciej@gmail.com>
 */
@RefreshScope
@Service
@Logging(pretty = true)
public class SampleServiceImpl implements SampleService
{
	@Value("${common.property:default}")
	private String commonProperty;

	@Override
	public Hello sayHello()
	{
		Hello hello = new Hello();
		hello.setMessage("Spring Cloud with CXF, Hello World!");

		return hello;
	}
}
