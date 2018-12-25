package org.bricks.framework.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "error")
public class RequestLimitController {

	/**
	 * 对高频ip限制，携带剩余限制时间
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/requestLimit")
	public Object requestLimitTime(HttpServletRequest request) {
		Long limitTime = (Long) request.getAttribute("remainingTime");
		return "IP已被限制，请间隔" + limitTime + "秒后在试。";
	}
}
