package top.jalo.commons.search.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Entity : Test ElasticSearch
 * 
 * @Author JALO
 *
 */
@Document(indexName = "test", type = "test")
public class ElasticSearchTestEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private String name;
	private String email;

	public ElasticSearchTestEntity() {
	}

	public ElasticSearchTestEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ElasticSearchTestEntity [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
