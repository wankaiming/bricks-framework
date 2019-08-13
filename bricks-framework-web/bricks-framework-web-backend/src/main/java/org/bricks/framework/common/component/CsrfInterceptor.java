package org.bricks.framework.common.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CsrfInterceptor extends HandlerInterceptorAdapter {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			// 如果是flash发出的请求，忽略token验证，解决firefox下flash获取不到当前用户sessionId的问题
			if ("Shockwave Flash".equals(request.getHeader("User-Agent"))) {
				return true;
			}

			String CsrfToken = CsrfTokenManager.getTokenFromRequest(request);
			String sessionToken = (String) request.getSession()
					.getAttribute(CsrfTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);

			Boolean isCurrentSitePost = CsrfTokenManager.getIsCurrentSitePost(request);

			if (CsrfToken == null || !CsrfToken.equals(sessionToken) || !isCurrentSitePost) {
				request.getSession().removeAttribute(CsrfTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
				response.setStatus(400);
				log.error("CsrfToken为空，或者与服务器端的不一致！");
				return false;
			}
		}

		return true;
	}
}
