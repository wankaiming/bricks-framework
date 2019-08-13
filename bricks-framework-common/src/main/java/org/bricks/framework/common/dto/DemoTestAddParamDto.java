package org.bricks.framework.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class DemoTestAddParamDto implements Serializable {
	
	private static final long serialVersionUID = -5593747470659210731L;
	
	private String id;

	@NotBlank(message="firstName不能为空")
	private String firstName;
	
	private String lastName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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