package top.jalo.commons.webservice.model;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sorter {

	public static enum Direction {
		ASC, DESC
	}
	
	private String property;
	private Direction direction;
	
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	public static List<Sorter> parse(String sort) {
		if (StringUtils.isEmpty(sort)) {
			return null;
		}
		
		try {
			OBJECT_MAPPER.readValue(sort, new TypeReference<List<Sorter>>() {
			});
		} catch (JsonParseException | JsonMappingException e) {
			return Arrays.stream(StringUtils.split(sort, ",")).map(property -> {
				Sorter sorter = new Sorter();
				if (property.charAt(0) == '-') {
					sorter.setDirection(Direction.DESC);
					sorter.setProperty(property.substring(1));
				} else {
					sorter.setDirection(Direction.ASC);
					sorter.setProperty(property);
				}
				return sorter;
			}).collect(Collectors.toList());
		} catch (IOException e) {
			return null;
		}
		
		return null;
	}
	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
