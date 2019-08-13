package org.bricks.framework.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// 防止流读取一次后就没有了, 所以需要将流继续写出去
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

		BodyReaderHttpServletResponseWrapper responseWrapper = new BodyReaderHttpServletResponseWrapper(
				httpServletResponse);

		String url = httpServletRequest.getRequestURL().toString();
		if (!isUpload(httpServletRequest)) {
			ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
			filterChain.doFilter(requestWrapper, responseWrapper);
		} else {
			ServletRequest requestWrapper = new HttpServletRequestWrapper(httpServletRequest);
			filterChain.doFilter(requestWrapper, responseWrapper);
		}

		byte[] bytes = responseWrapper.getBytes(); // 获取缓存的响应数据

		if (log.isDebugEnabled()) {
			if (StringUtils.isNotBlank(httpServletRequest.getQueryString())) {
				url += "?" + httpServletRequest.getQueryString();
			}

			StringBuilder logRender = new StringBuilder("Outbound Message\n");
			logRender.append("----------------------------\n");
			logRender.append("ThreadName: ").append(Thread.currentThread().getName()).append("\n");
			logRender.append("RequestUrl: ").append(url).append("\n");
			logRender.append("ResponseData: ").append(new String(bytes, "utf-8")).append("\n");
			logRender.append("----------------------------------------\n");
			log.debug(logRender.toString());
		}

		httpServletResponse.setContentLength(-1);
		httpServletResponse.getOutputStream().write(bytes);
	}

	@Override
	public void destroy() {

	}
	
	private boolean isUpload(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        if (multipartResolver.isMultipart(request)) {
        	return true;
        }
		return false;
	}
}
