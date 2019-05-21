package org.bricks.framework.common.controller;

import javax.validation.Valid;

import org.bricks.framework.common.dao.DemoEsRepository;
import org.bricks.framework.common.dto.BaseDto;
import org.bricks.framework.common.dto.DemoTestAddParamDto;
import org.bricks.framework.common.dto.DemoTestDto;
import org.bricks.framework.common.utils.ValidationErrorMsgGetUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.nacos.api.config.annotation.NacosValue;

@RestController
@RequestMapping(value = "/demo")
public class DemoRestController extends BaseRestController {

	@NacosValue(value = "${name:哈哈}", autoRefreshed = true)
	private String name;
	
	@Autowired
	private DemoEsRepository demoEsRepository;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String get() {
		return "返回结果: 测试的name：" + name;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseDto add(@RequestBody @Valid DemoTestAddParamDto param, BindingResult result) {
		if (result.hasErrors()) {
			return new BaseDto(false, ValidationErrorMsgGetUtils.getMsg(result));
		}
		DemoTestDto DemoTestDto = new DemoTestDto();
		BeanUtils.copyProperties(param, DemoTestDto);
		demoEsRepository.save(DemoTestDto);
		return new BaseDto();
	}

	@RequestMapping("/query/{id}")
	public DemoTestDto query(@PathVariable("id") String id) {
		DemoTestDto demoInfo = demoEsRepository.queryDemoInfoById(id);
		return demoInfo;
	}

}