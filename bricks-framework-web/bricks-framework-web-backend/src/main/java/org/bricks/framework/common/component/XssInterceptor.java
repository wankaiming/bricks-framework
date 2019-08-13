package org.bricks.framework.common.component;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class XssInterceptor extends HandlerInterceptorAdapter {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			Map<String, String[]> params = request.getParameterMap();
			if (null != params && params.size() > 0) {

				for (String key : params.keySet()) {

					String value = Arrays.toString(params.get(key));
					if (XssCheckUtil.checkIsExistXss(value)) {
						response.setStatus(403);
						log.error("请求参数为" + key + "的字段，存在非法字符！");
						return false;
					}
				}
			}
		}

		return true;
	}
}
