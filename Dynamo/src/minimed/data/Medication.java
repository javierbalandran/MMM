package minimed.data;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Medication {
	
	private String name;
	private String description;
	private String source;
	private int amount;
	private int interval;
	
	public Medication(String name, String description, String source,
			int amount, int interval, Date startDate, Date endDate,
			boolean currentlyTaking) {
		this.name = name;
		this.description = description;
		this.source = source;
		this.amount = amount;
		this.interval = interval;
	}
	
	public Medication(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		try {
			values = mapper.readValue(json, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		this.name = (String) values.get(FieldNames.MEDICATION.NAME);
		this.description = (String) values.get(FieldNames.MEDICATION.DESCRIPTION);
		this.source = (String) values.get(FieldNames.MEDICATION.SOURCE);
		this.amount = (Integer) values.get(FieldNames.MEDICATION.AMOUNT);
		this.interval = (Integer) values.get(FieldNames.MEDICATION.INTERVAL);
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
//		values.put(FieldNames.USER.FIRST_NAME, firstName);
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
