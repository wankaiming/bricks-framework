package org.bricks.framework.common.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitMQConfiguration.SIMPLE_QUEUE_NAME)
public class MsgReceiver {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RabbitHandler
	public void process(String content) {
		logger.info("接收处理SIMPLE_QUEUE队列当中的消息： " + content);
	}

}