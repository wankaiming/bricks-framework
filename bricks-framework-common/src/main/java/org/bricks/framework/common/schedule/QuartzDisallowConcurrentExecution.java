package org.bricks.framework.common.schedule;

import org.bricks.framework.common.entity.ScheduleTask;
import org.bricks.framework.common.utils.ScheduleTaskUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleTask task = (ScheduleTask) context.getMergedJobDataMap().get("task");
		ScheduleTaskUtils.invokMethod(task);
	}

}
