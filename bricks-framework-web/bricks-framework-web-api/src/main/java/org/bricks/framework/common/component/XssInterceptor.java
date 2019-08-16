package org.bricks.framework.common.component;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.bricks.framework.common.filter.BodyReaderHttpServletRequestWrapper;
import org.bricks.framework.common.utils.XssCheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class XssInterceptor extends HandlerInterceptorAdapter {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * xss 拦截
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @author wankaiming
	 * @time 2019年5月29日 上午10:31:16
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURL().toString();

		if ("POST".equalsIgnoreCase(request.getMethod())) {

			Map<String, String[]> params = request.getParameterMap();
			if (null != params && params.size() > 0) {

				for (String key : params.keySet()) {

					String value = Arrays.toString(params.get(key));
					if (XssCheckUtil.checkIsExistXss(value)) {
						response.setStatus(403);
						log.error("请求参数为:" + key + "的字段，存在非法字符！ 字符内容为:" + value);
						return false;
					}
				}
			}

			if (!isUpload(request)) {

				String body = new BodyReaderHttpServletRequestWrapper(request).getBodyString(request);

				if (log.isDebugEnabled()) {
					if (StringUtils.isNotBlank(request.getQueryString())) {
						url += "?" + request.getQueryString();
					}

					StringBuilder logRender = new StringBuilder("Inbound Message\n");
					logRender.append("----------------------------\n");
					logRender.append("ThreadName: ").append(Thread.currentThread().getName()).append("\n");
					logRender.append("RequestUrl: ").append(url).append("\n");
					logRender.append("PostData: ").append(null != body ? body : "").append("\n");
					logRender.append("----------------------------------------\n");
					log.debug(logRender.toString());
				}

				if (null != body && !"".equals(body)) {
					if (XssCheckUtil.checkIsExistXss(body)) {
						response.setStatus(403);
						log.error("请求参数RequestBody的内容，存在非法字符！RequestBody的内容为:" + body);
						return false;
					}
				}
			}

		} else {
			if (log.isDebugEnabled()) {
				if (StringUtils.isNotBlank(request.getQueryString())) {
					url += "?" + request.getQueryString();
				}

				StringBuilder logRender = new StringBuilder("Inbound Message\n");
				logRender.append("----------------------------\n");
				logRender.append("ThreadName: ").append(Thread.currentThread().getName()).append("\n");
				logRender.append("RequestUrl: ").append(url).append("\n");
				logRender.append("----------------------------------------\n");
				log.debug(logRender.toString());
			}
		}

		return true;
	}

	private boolean isUpload(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (multipartResolver.isMultipart(request)) {
			return true;
		}
		return false;
	}
}
