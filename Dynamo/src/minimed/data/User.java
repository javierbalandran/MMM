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
	private String middleInitial;
	private String lastName;
	private String birthDate;
	private BloodType bloodType;
	private Gender gender;

	public User(String firstName, String middleInitial, String lastName,
			String birthDate, BloodType bloodType, Gender gender) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.bloodType = bloodType;
		this.gender = gender;
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
		this.middleInitial = (String) values.get(FieldNames.USER.MIDDLE_INITIAL);
		this.lastName = (String) values.get(FieldNames.USER.LAST_NAME);
		this.birthDate = (String) values.get(FieldNames.USER.DOB);
		this.bloodType = BloodType.valueOf((String)values.get(FieldNames.USER.BLOOD_TYPE));
		this.gender = Gender.valueOf((String)values.get(FieldNames.USER.GENDER));
	}
	
	public String getJSONString() {
		String blobString = null;
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> values = new HashMap<String, Object>();
		
		values.put(FieldNames.USER.FIRST_NAME, firstName);
		values.put(FieldNames.USER.MIDDLE_INITIAL, middleInitial);
		values.put(FieldNames.USER.LAST_NAME, lastName);
		values.put(FieldNames.USER.DOB, birthDate);
		values.put(FieldNames.USER.BLOOD_TYPE, bloodType.name());
		values.put(FieldNames.USER.GENDER, gender.name());
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}

}
