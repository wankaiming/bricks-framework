package org.bricks.framework.common.controller;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.bricks.framework.common.dto.BaseDto;
import org.bricks.framework.common.entity.ScheduleTask;
import org.bricks.framework.common.service.ScheduleTaskService;
import org.bricks.framework.common.utils.SpringBootContextUtils;
import org.quartz.CronScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/scheduleConfig")
public class ScheduleConfigController {

	private static final Logger log = LoggerFactory.getLogger(ScheduleConfigController.class);

	@Autowired
	private ScheduleTaskService scheduleTaskService;

	@RequestMapping("/list")
	public String list(Map<String, Object> model) {
		List<ScheduleTask> taskList = scheduleTaskService.getAllTask();
		model.put("taskList", taskList);
		return "scheduleConfig/list";
	}

	@RequestMapping("/addTask")
	@ResponseBody
	public BaseDto addTask(HttpServletRequest request, ScheduleTask task) {
		try {
			CronScheduleBuilder.cronSchedule(task.getCronExpression());
		} catch (Exception e) {
			log.error("cron表达式有误，不能被解析: ", e);
			return new BaseDto(false, "cron表达式有误，不能被解析！");
		}
		Object obj = null;
		try {
			// Spring Id 优先级大于 BeanClass
			if (StringUtils.isNotBlank(task.getSpringId())) {
				obj = SpringBootContextUtils.getBean(task.getSpringId());
			} else {
				Class<? extends Object> clazz = Class.forName(task.getBeanClass());
				obj = clazz.newInstance();
			}
		} catch (Exception e) {
			log.error("BeanClass或者SpringID初始化异常: ", e);
			return new BaseDto(false, "BeanClass或者SpringID初始化异常...");
		}
		if (obj == null) {
			return new BaseDto(false, "未找到目标类！");
		} else {
			Class<? extends Object> clazz = obj.getClass();
			Method method = null;
			try {
				method = clazz.getMethod(task.getMethodName());
			} catch (Exception e) {
				log.error("未找到目标类: ", e);
				return new BaseDto(false, "未找到目标类！");
			}
			if (method == null) {
				return new BaseDto(false, "未找到目标类！");
			}
		}

		Boolean flag = scheduleTaskService.addTask(task);
		if (flag) {
			return new BaseDto();
		} else {
			return new BaseDto(false, "保存失败，检查 name group 组合是否有重复！");
		}
	}

	@RequestMapping("/changeStatus")
	@ResponseBody
	public BaseDto changeStatus(Integer id, String cmd) {
		Boolean flag = scheduleTaskService.changeStatus(id, cmd);
		if (flag) {
			return new BaseDto();
		} else {
			return new BaseDto(false, "任务状态改变失败！");
		}
	}

	@RequestMapping("/updateCron")
	@ResponseBody
	public BaseDto updateCron(Integer id, String cron) {
		try {
			CronScheduleBuilder.cronSchedule(cron);
		} catch (Exception e) {
			log.error("cron表达式有误，不能被解析: ", e);
			return new BaseDto(false, "cron表达式有误，不能被解析！");
		}
		Boolean flag = scheduleTaskService.updateCron(id, cron);
		if (flag) {
			return new BaseDto();
		} else {
			return new BaseDto(false, "cron更新失败！");
		}
	}

	@RequestMapping("/runTask")
	@ResponseBody
	public BaseDto runTask(Integer id) {
		Boolean flag = scheduleTaskService.runTask(id);
		if (flag) {
			return new BaseDto();
		} else {
			return new BaseDto(false, "任务执行失败！");
		}
	}
	
	@RequestMapping("/delTask")
	@ResponseBody
	public BaseDto delTask(Integer id) {
		Boolean flag = scheduleTaskService.deleteTask(id);
		if (flag) {
			return new BaseDto();
		} else {
			return new BaseDto(false, "删除失败！");
		}
	}
}
