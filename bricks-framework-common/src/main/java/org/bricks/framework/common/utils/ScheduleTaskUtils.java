package org.bricks.framework.common.utils;

import java.lang.reflect.Method;

import org.bricks.framework.common.entity.ScheduleTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ScheduleTaskUtils {

	private static final Logger log = LoggerFactory.getLogger(ScheduleTaskUtils.class);

	@SuppressWarnings("unchecked")
	public static void invokMethod(ScheduleTask scheduleTask) {
		Object object = null;
		@SuppressWarnings("rawtypes")
		Class clazz;
		if (!StringUtils.isEmpty(scheduleTask.getSpringId())) {
			object = SpringBootContextUtils.getBean(scheduleTask.getSpringId());
		} else if (!StringUtils.isEmpty(scheduleTask.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleTask.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		if (object == null) {
			log.error("任务名称 ： [" + scheduleTask.getJobName() + "]---------------未启动成功，请检查是否配置正确！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleTask.getMethodName());
		} catch (NoSuchMethodException e) {
			log.error("任务名称 ： [" + scheduleTask.getJobName() + "]---------------未启动成功，方法名设置错误！", e);
		} catch (SecurityException e) {
			log.error(e.getMessage(), e);
		}
		if (method != null) {
			try {
				method.invoke(object);
				log.info("任务名称 ： [" + scheduleTask.getJobName() + "]---------------启动成功！");
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} else {
			log.error("任务名称 ： [" + scheduleTask.getJobName() + "]---------------未启动成功，方法名设置错误！");
		}
	}
}
