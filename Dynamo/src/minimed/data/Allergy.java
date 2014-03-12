package minimed.data;

import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

public class Allergy {
	private String name;
	private String details;
	
	public Allergy(Map<String, AttributeValue> item) {
		item.get(AllergyAccessor.NAME);
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
