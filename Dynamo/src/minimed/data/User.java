package minimed.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

	private String firstName;
	private String lastName;
	private String birthDate;
	private BloodType bloodType;
	
	public User(String firstName, String lastName, String birthDate,
			BloodType bloodType) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.bloodType = bloodType;
	}

	public User(String json) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		try {
			values = mapper.readValue(json, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		this.firstName = (String) values.get(FieldNames.USER.FIRST_NAME);
		this.lastName = (String) values.get(FieldNames.USER.LAST_NAME);
		this.birthDate = (String) values.get(FieldNames.USER.DOB);
		this.bloodType = BloodType.valueOf((String)values.get(FieldNames.USER.BLOOD_TYPE));
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		values.put(FieldNames.USER.FIRST_NAME, firstName);
		values.put(FieldNames.USER.LAST_NAME, lastName);
		values.put(FieldNames.USER.DOB, birthDate);
		values.put(FieldNames.USER.BLOOD_TYPE, bloodType.name());
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}

	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", birthDate=" + birthDate + ", bloodType=" + bloodType + "]";
	}

	
}
