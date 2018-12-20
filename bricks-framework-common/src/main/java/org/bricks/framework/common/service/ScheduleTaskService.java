package org.bricks.framework.common.service;

import java.util.List;

import org.bricks.framework.common.entity.ScheduleTask;

public interface ScheduleTaskService {

	/**
	 * 添加一个任务
	 * 
	 * @param task
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:06:48
	 */
	Boolean addTask(ScheduleTask task);

	/**
	 * 删除一个任务
	 * 
	 * @param id
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:05
	 */
	Boolean deleteTask(Integer id);

	/**
	 * 获取所有任务
	 * 
	 * @return
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:17
	 */
	List<ScheduleTask> getAllTask();

	/**
	 * 更改任务状态（停止/恢复）
	 * 
	 * @param id
	 * @param cmd
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:30
	 */
	Boolean changeStatus(Integer id, String cmd);

	/**
	 * 更改任务 cron表达式
	 * 
	 * @param id
	 * @param cron
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:39
	 */
	Boolean updateCron(Integer id, String cron);
	
	/**
	 * 立即执行任务
	 * 
	 * @param id
	 * @author wankaiming
	 * @time 2018年10月25日 下午7:07:48
	 */
	Boolean runTask(Integer id);
	
	/**
	 * 初始化定时器任务
	 * 
	 * @author wankaiming
	 * @time 2018年12月20日 下午7:30:38
	 */
	void runExistTask();
}
