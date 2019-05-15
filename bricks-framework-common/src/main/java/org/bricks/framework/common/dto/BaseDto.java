package org.bricks.framework.common.dto;

import java.io.Serializable;

public class BaseDto implements Serializable {

	private static final long serialVersionUID = -5294062212252261609L;

	private String msg;

	private String code;

	private Boolean flag;

	public BaseDto() {
		this.msg = "OK";
		this.code = "0";
		this.flag = true;
	}

	public BaseDto(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public BaseDto(Boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}
	
	public BaseDto(Boolean flag, String code, String msg) {
		this.flag = flag;
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
