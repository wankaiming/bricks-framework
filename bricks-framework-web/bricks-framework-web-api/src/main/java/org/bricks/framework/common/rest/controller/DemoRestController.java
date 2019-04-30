package org.bricks.framework.common.rest.controller;

import org.bricks.framework.common.dao.DemoEsRepository;
import org.bricks.framework.common.dto.DemoTestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	private DemoEsRepository demoEsRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		return "返回结果: 测试的name：" + name;
	}
	
	@RequestMapping("/add/{id}")
	public String add(@PathVariable("id") String id) {
		DemoTestDto DemoTestDto = new DemoTestDto();
		DemoTestDto.setId(id);
		DemoTestDto.setFirstName("张");
		DemoTestDto.setLastName("三");
		demoEsRepository.save(DemoTestDto);
		return "success";
	}

	@RequestMapping("/query/{id}")
	public DemoTestDto query(@PathVariable("id") String id) {
		DemoTestDto demoInfo = demoEsRepository.queryDemoInfoById(id);
		return demoInfo;
	}

}