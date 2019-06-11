package org.bricks.framework.common.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.bricks.framework.common.constant.TaskConstant;
import org.bricks.framework.common.dao.ScheduleTaskMapper;
import org.bricks.framework.common.entity.ScheduleTask;
import org.bricks.framework.common.entity.ScheduleTaskExample;
import org.bricks.framework.common.schedule.QuartzAllowConcurrentExecution;
import org.bricks.framework.common.schedule.QuartzDisallowConcurrentExecution;
import org.bricks.framework.common.service.ScheduleTaskService;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

	public final Logger log = LoggerFactory.getLogger(ScheduleTaskServiceImpl.class);

	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ScheduleTaskMapper scheduleTaskMapper;

	/**
	 * 添加一个任务
	 * 
	 * @param task
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:06:48
	 */
	@Override
	public Boolean addTask(ScheduleTask task) {
		task.setCreateTime(new Date());
		scheduleTaskMapper.insertSelective(task);
		return true;
	}

	private void addJob(ScheduleTask task) throws SchedulerException, UnsupportedEncodingException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();

		TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

		Class<? extends Job> clazz = TaskConstant.CONCURRENT_YES.equals(task.getIsConcurrent())
				? QuartzAllowConcurrentExecution.class : QuartzDisallowConcurrentExecution.class;

		JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(task.getJobName(), task.getJobGroup()).build();

		jobDetail.getJobDataMap().put("task", task);

		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());

		trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		scheduler.scheduleJob(jobDetail, trigger);
	}

	/**
	 * 删除一个任务
	 * 
	 * @param id
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:05
	 */
	@Override
	public Boolean deleteTask(Integer id) {
		ScheduleTask task = scheduleTaskMapper.selectByPrimaryKey(id);
		if (null == task) {
			return false;
		}
		scheduleTaskMapper.deleteByPrimaryKey(id);
		return true;
	}

	private void delJob(ScheduleTask task) throws SchedulerException, UnsupportedEncodingException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 获取所有任务
	 * 
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:17
	 */
	@Override
	public List<ScheduleTask> getAllTask() {
		ScheduleTaskExample example = new ScheduleTaskExample();
		return scheduleTaskMapper.selectByExample(example);
	}

	/**
	 * 更改任务状态（停止/恢复）
	 * 
	 * @param id
	 * @param cmd
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:30
	 */
	@Override
	public Boolean changeStatus(Integer id, String cmd) {
		ScheduleTask task = scheduleTaskMapper.selectByPrimaryKey(id);
		if (null == task) {
			return false;
		}
		try {
			if ("stop".equals(cmd)) {
				delJob(task);
				task.setJobStatus(TaskConstant.STATUS_NOT_RUNNING);
			} else if ("start".equals(cmd)) {
				addJob(task);
				task.setJobStatus(TaskConstant.STATUS_RUNNING);
			}
		} catch (Exception e) {
			log.error("更改任务状态出现异常", e);
			return false;
		}
		scheduleTaskMapper.updateByPrimaryKeySelective(task);
		return true;
	}

	/**
	 * 更改任务 cron表达式
	 * 
	 * @param id
	 * @param cron
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:39
	 */
	@Override
	public Boolean updateCron(Integer id, String cron) {
		ScheduleTask task = scheduleTaskMapper.selectByPrimaryKey(id);
		if (null == task) {
			return false;
		}
		task.setCronExpression(cron);
		task.setUpdateTime(new Date());
		scheduleTaskMapper.updateByPrimaryKeySelective(task);
		return true;
	}

	/**
	 * 立即执行任务
	 * 
	 * @param id
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:48
	 */
	@Override
	public Boolean runTask(Integer id) {
		ScheduleTask task = scheduleTaskMapper.selectByPrimaryKey(id);
		if (null == task) {
			return false;
		}

		try {
			runJob(task);
		} catch (Exception e) {
			log.error("更改任务状态出现异常", e);
			return false;
		}
		return true;

	}

	private void runJob(ScheduleTask task) throws SchedulerException, UnsupportedEncodingException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
		scheduler.triggerJob(jobKey);
	}
	
	/**
	 * 初始化定时器任务
	 * 
	 * @author wankaiming
	 * @time 2018年12月20日 下午7:30:38
	 */
	public void runExistTask() {
		List<ScheduleTask> list = getAllTask();
		try {
			if (!CollectionUtils.isEmpty(list)) {
				for (ScheduleTask row : list) {
					addJob(row);
					log.info("name:{} group:{} 定时器启动成功！", row.getJobName(), row.getJobGroup());
				}
			}
		} catch (UnsupportedEncodingException | SchedulerException e) {
			log.error("启动时初始化定时器任务出现异常", e);
		}
	}

}
