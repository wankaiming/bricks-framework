package org.bricks.framework.common.service.impl;

import javax.annotation.PostConstruct;

import org.bricks.framework.common.service.ScheduleTaskInitService;
import org.bricks.framework.common.service.ScheduleTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTaskInitServiceImpl implements ScheduleTaskInitService {

	public final Logger log = LoggerFactory.getLogger(ScheduleTaskInitServiceImpl.class);

	@Autowired
	private ScheduleTaskService scheduleTaskService;

	/**
	 * 启动时运行之前已经设置为开启的定时器
	 * 
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:08:48
	 */
	@PostConstruct
	public void init() {
		scheduleTaskService.runExistTask();
	}

}
