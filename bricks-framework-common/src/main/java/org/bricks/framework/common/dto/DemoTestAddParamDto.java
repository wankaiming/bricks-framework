package org.bricks.framework.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.bricks.framework.common.validator.SafeTextOrHtml;

public class DemoTestAddParamDto implements Serializable {
	
	private static final long serialVersionUID = -5593747470659210731L;
	
	private Integer id;

	@NotBlank(message="firstName不能为空")
	@SafeTextOrHtml(message="firstName存在非法字符")
	private String firstName;
	
	@SafeTextOrHtml(message="lastName存在非法字符")
	private String lastName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}