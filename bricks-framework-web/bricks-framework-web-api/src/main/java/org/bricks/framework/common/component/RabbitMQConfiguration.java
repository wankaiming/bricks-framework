package org.bricks.framework.common.component;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	public static final String SIMPLE_QUEUE_NAME = "SIMPLE_QUEUE_NAME";

	@Bean
	public Queue queue() {
		return new Queue(SIMPLE_QUEUE_NAME, true, false, false, null);
	}

}