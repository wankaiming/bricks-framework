package org.bricks.framework.common.schedule;

import org.bricks.framework.common.entity.ScheduleTask;
import org.bricks.framework.common.utils.ScheduleTaskUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzAllowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleTask task = (ScheduleTask) context.getMergedJobDataMap().get("task");
		ScheduleTaskUtils.invokMethod(task);
	}

}
