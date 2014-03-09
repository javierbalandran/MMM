package minimed.data;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MedicalHistory {
	
	private String eventTitle;
	public String description;
	public String date;

	
	public MedicalHistory(String eventTitle, String description, String date) {
		this.eventTitle = eventTitle;
		this.description = description;
		this.date = date;
	}

	public MedicalHistory(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		try {
			values = mapper.readValue(json, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		this.eventTitle = (String) values.get(FieldNames.MEDICAL_HISTORY.EVENT_TITLE);
		this.description = (String) values.get(FieldNames.MEDICAL_HISTORY.DESCRIPTION);
		this.date = (String) values.get(FieldNames.MEDICAL_HISTORY.DATE);
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		values.put(FieldNames.MEDICAL_HISTORY.EVENT_TITLE, eventTitle);
		values.put(FieldNames.MEDICAL_HISTORY.DESCRIPTION, description);
		values.put(FieldNames.MEDICAL_HISTORY.DATE, date);
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getSrescription() {
		return description;
	}

	public void setSrescription(String srescription) {
		this.description = srescription;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
