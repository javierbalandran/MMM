package minimed.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Allergy {
	
	private String name;
	private AllergyType type;
	private String details;
	
	
	public Allergy(String name, AllergyType type, String details) {
		this.name = name;
		this.type = type;
		this.details = details;
	}

	public Allergy(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		try {
			values = mapper.readValue(json, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		this.name = (String) values.get(FieldNames.ALLERGY.NAME);
		this.type = AllergyType.valueOf((String)values.get(FieldNames.ALLERGY.TYPE));
		this.details = (String) values.get(FieldNames.ALLERGY.DETAILS);
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		values.put(FieldNames.ALLERGY.NAME, name);
		values.put(FieldNames.ALLERGY.TYPE, type.name());
		values.put(FieldNames.ALLERGY.DETAILS, details);
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
