package org.bricks.framework.common.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "demo", type = "demo_test", shards = 1, replicas = 0, refreshInterval = "-1")
public class DemoTestDto implements Serializable {
	
	private static final long serialVersionUID = -8623791464116967291L;

	@Id
	private String id;
	
	@Field(index=true,type=FieldType.Text)
	private String firstName;
	
	@Field(index=true,type=FieldType.Text)
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

	@Override
	public String toString() {
		return "DemoTestDto [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}

}
