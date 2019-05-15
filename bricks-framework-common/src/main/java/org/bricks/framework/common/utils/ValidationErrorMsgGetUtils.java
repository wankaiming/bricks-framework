package org.bricks.framework.common.utils;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class ValidationErrorMsgGetUtils {

	public static String getMsg(BindingResult result) {
		List<ObjectError> errorList = result.getAllErrors();
		StringBuilder msg = new StringBuilder();
		for (ObjectError error : errorList) {
			String content = error.getDefaultMessage();
			String txtcontent = content.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
			txtcontent = txtcontent.replaceAll("\\s*|\t|\r|\n", ""); // 去除字符串中的空格,回车,换行符,制表符
			msg.append(txtcontent + "<br/>");
		}
		return msg.toString();
	}

}