package org.bricks.framework.common.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CsrfInterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HandlerInterceptor() {
			
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
						return false;
					}
				}

				return true;
			}

		}).addPathPatterns("/**");
	}
}
