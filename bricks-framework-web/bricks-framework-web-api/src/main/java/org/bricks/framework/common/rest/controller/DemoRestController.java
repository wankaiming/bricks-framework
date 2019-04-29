package org.bricks.framework.common.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.api.config.annotation.NacosValue;

@RestController
@RequestMapping(value = "/demo")
public class DemoRestController {

	@NacosValue(value = "${name:哈哈}", autoRefreshed = true)
	private String name;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		return "返回结果: 测试的name：" + name;
	}

}