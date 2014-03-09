package minimed.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Contact {

	private ContactType type;
	private String name;
	private String relation;
	private String homePhoneNumber;
	private String cellPhoneNumber;
	private String workPhoneNumber;
	
	public Contact(ContactType type, String name, String relation,
		String homePhoneNumber, String cellPhoneNumber, String workPhoneNumber) {
		Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	    Matcher matcher = pattern.matcher(homePhoneNumber);
	    
	    if (!matcher.matches()) {
	    	//phone number doesn't match XXX-XXXXXXX
	    }
		
		this.type = type;
		this.name = name;
		this.relation = relation;
		this.homePhoneNumber = homePhoneNumber;
		this.cellPhoneNumber = cellPhoneNumber;
		this.workPhoneNumber = workPhoneNumber;
	}


	public Contact(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		try {
			values = mapper.readValue(json, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		this.type = ContactType.valueOf((String)values.get(FieldNames.CONTACT.TYPE));
		this.name = (String) values.get(FieldNames.CONTACT.NAME);
		this.relation = (String) values.get(FieldNames.CONTACT.RELATION);
		this.homePhoneNumber = (String) values.get(FieldNames.CONTACT.HOME_PHONE);
		this.cellPhoneNumber = (String) values.get(FieldNames.CONTACT.CELL_PHONE);
		this.workPhoneNumber = (String) values.get(FieldNames.CONTACT.WORK_PHONE);
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
//		values.put(FieldNames.USER.FIRST_NAME, firstName);
		
		values.put(FieldNames.CONTACT.TYPE, type.name());
		values.put(FieldNames.CONTACT.NAME, name);
		values.put(FieldNames.CONTACT.RELATION, relation);
		values.put(FieldNames.CONTACT.HOME_PHONE, homePhoneNumber);
		values.put(FieldNames.CONTACT.CELL_PHONE, cellPhoneNumber);
		values.put(FieldNames.CONTACT.WORK_PHONE, workPhoneNumber);
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}
	
	public ContactType getType() {
		return type;
	}
	public void setType(ContactType type) {
		this.type = type;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}


	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}


	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}


	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}


	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}


	public void setWorkPhoneNumber(String workPhoneNumber) {
		this.workPhoneNumber = workPhoneNumber;
	}

	
	
}
