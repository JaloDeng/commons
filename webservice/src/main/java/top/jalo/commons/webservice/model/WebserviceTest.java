package top.jalo.commons.webservice.model;

import java.util.Date;

/**
 * Model : Test
 * 
 * @Author JALO
 * 
 */
public class WebserviceTest {

	private Long id;
	
	private String name;
	
	private Integer age;
	
	private String email;
	
	private Long createBy;
	
	private Date createDate;
	
	private Long lastModifiedBy;
	
	private Date lastModifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "WebserviceTest [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + ", createDate="
				+ createDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
