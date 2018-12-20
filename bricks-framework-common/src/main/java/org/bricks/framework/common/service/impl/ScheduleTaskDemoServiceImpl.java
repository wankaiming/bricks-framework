package org.bricks.framework.common.service.impl;

import org.bricks.framework.common.service.ScheduleTaskDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskDemoServiceImpl implements ScheduleTaskDemoService {
	
	public final Logger log = LoggerFactory.getLogger(ScheduleTaskDemoServiceImpl.class);

	@Override
	public void demo() {
		log.info("test");
	}

}
