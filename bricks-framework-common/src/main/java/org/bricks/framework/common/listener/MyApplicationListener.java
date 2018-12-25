package org.bricks.framework.common.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class MyApplicationListener implements ServletContextListener {
	
	private Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		// IP存储器
		Map<String, Long[]> ipMap = new HashMap<String, Long[]>();
		context.setAttribute("ipMap", ipMap);
		// 限制IP存储器：存储被限制的IP信息
		Map<String, Long> limitedIpMap = new HashMap<String, Long>();
		context.setAttribute("limitedIpMap", limitedIpMap);
		logger.info("ipMap和limitedIpMap 初始化成功");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
