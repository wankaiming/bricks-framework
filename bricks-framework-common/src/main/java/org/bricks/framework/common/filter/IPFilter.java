package org.bricks.framework.common.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bricks.framework.common.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = "/*")
public class IPFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(IPFilter.class);

	/**
	 * 默认限制时间（单位：ms）
	 */
	private static final long LIMITED_TIME_MILLIS = 10 * 1000;

	/**
	 * 用户连续访问最高阀值，超过该值则认定为恶意操作的IP，进行限制
	 */
	private static final int LIMIT_NUMBER = 20;

	/**
	 * 用户访问最小安全时间，在该时间内如果访问次数大于阀值，则记录为恶意IP，否则视为正常访问（单位：ms）
	 */
	private static final int MIN_SAFE_TIME = 1000;
	
	// 以上三个合起来理解就是：1秒钟如果连续访问20次，那么就出现限制访问的提示，限制时长为10秒

	private FilterConfig config;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		ServletContext context = config.getServletContext();
		// 获取限制IP存储器：存储被限制的IP信息
		Map<String, Long> limitedIpMap = (Map<String, Long>) context.getAttribute("limitedIpMap");
		// 过滤受限的IP
		filterLimitedIpMap(limitedIpMap);
		// 获取用户IP
		String ip = IPUtils.getIpAddress(request);
		logger.info("ip:" + ip);
		// 判断是否是被限制的IP，如果是，则输出异常信息
		if (isLimitedIP(limitedIpMap, ip)) {
			long limitedTime = limitedIpMap.get(ip) - System.currentTimeMillis();
			// 剩余限制时间(用为从毫秒到秒转化的一定会存在些许误差，但基本可以忽略不计)
			request.setAttribute("remainingTime", ((limitedTime / 1000) + (limitedTime % 1000 > 0 ? 1 : 0)));
			logger.info("ip访问过于频繁：" + ip);
			request.getRequestDispatcher("/error/requestLimit").forward(request, response);
			return;
		}
		// 获取IP存储器
		Map<String, Long[]> ipMap = (Map<String, Long[]>) context.getAttribute("ipMap");
		// 判断存储器中是否存在当前IP，如果没有则为初次访问，初始化该ip
		// 如果存在当前ip，则验证当前ip的访问次数
		// 如果大于限制阀值，判断达到阀值的时间，如果不大于[用户访问最小安全时间]则视为恶意访问，输出异常信息
		if (ipMap.containsKey(ip)) {
			Long[] ipInfo = ipMap.get(ip);
			ipInfo[0] = ipInfo[0] + 1;
			logger.info("当前第[" + (ipInfo[0]) + "]次访问，访问时间为：" + (ipInfo[1]));
			if (ipInfo[0] > LIMIT_NUMBER) {
				Long ipAccessTime = ipInfo[1];
				Long currentTimeMillis = System.currentTimeMillis();
				if (currentTimeMillis - ipAccessTime <= MIN_SAFE_TIME) {
					limitedIpMap.put(ip, currentTimeMillis + LIMITED_TIME_MILLIS);
					request.setAttribute("remainingTime", LIMITED_TIME_MILLIS / 1000);
					logger.info("ip访问过于频繁：" + ip);
					request.getRequestDispatcher("/error/requestLimit").forward(request, response);
					return;
				} else {
					initIpVisitsNumber(ipMap, ip);
				}
			}
		} else {
			initIpVisitsNumber(ipMap, ip);
			logger.info("您首次访问该网站");
		}
		context.setAttribute("ipMap", ipMap);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	/**
	 * 过滤受限的IP，剔除已经到期的限制IP
	 * 
	 * @param limitedIpMap
	 */
	private void filterLimitedIpMap(Map<String, Long> limitedIpMap) {
		if (limitedIpMap == null) {
			return;
		}
		Set<String> keys = limitedIpMap.keySet();
		Iterator<String> keyIt = keys.iterator();
		long currentTimeMillis = System.currentTimeMillis();
		while (keyIt.hasNext()) {
			long expireTimeMillis = limitedIpMap.get(keyIt.next());
			if (expireTimeMillis <= currentTimeMillis) {
				keyIt.remove();
			}
		}
	}

	/**
	 * 是否是被限制的IP
	 * 
	 * @param limitedIpMap
	 * @param ip
	 * @return true : 被限制 | false : 正常
	 */
	private boolean isLimitedIP(Map<String, Long> limitedIpMap, String ip) {
		if (limitedIpMap == null || ip == null) {
			// 没有被限制
			return false;
		}
		Set<String> keys = limitedIpMap.keySet();
		Iterator<String> keyIt = keys.iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			if (key.equals(ip)) {
				// 被限制的IP
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化用户访问次数和访问时间
	 * 
	 * @param ipMap
	 * @param ip
	 */
	private void initIpVisitsNumber(Map<String, Long[]> ipMap, String ip) {
		Long[] ipInfo = new Long[2];
		ipInfo[0] = 0L;// 访问次数
		ipInfo[1] = System.currentTimeMillis();// 初次访问时间
		ipMap.put(ip, ipInfo);
	}

}
