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
			int amount, int interval) {
		super();
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
		
		values.put(FieldNames.MEDICATION.NAME, name);
		values.put(FieldNames.MEDICATION.DESCRIPTION, description);
		values.put(FieldNames.MEDICATION.SOURCE, source);
		values.put(FieldNames.MEDICATION.AMOUNT, amount);
		values.put(FieldNames.MEDICATION.INTERVAL, interval);
		
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

	@Override
	public String toString() {
		return "Medication [name=" + name + ", description=" + description
				+ ", source=" + source + ", amount=" + amount + ", interval="
				+ interval + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Medication other = (Medication) obj;
		if (amount != other.amount)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (interval != other.interval)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}
	
	

}
