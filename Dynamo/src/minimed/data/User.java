package minimed.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {

	private String password;
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String birthDate;
	private BloodType bloodType;
	private Gender gender;

	public User(String firstName, String middleInitial, String lastName,
			String birthDate, BloodType bloodType, Gender gender, String password) {
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.bloodType = bloodType;
		this.gender = gender;
		this.password = password;
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
		this.password = (String) values.get(FieldNames.USER.PASSWORD);
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
		values.put(FieldNames.USER.PASSWORD, password);
		
		try {
			blobString = mapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		return blobString;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (bloodType != other.bloodType)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleInitial == null) {
			if (other.middleInitial != null)
				return false;
		} else if (!middleInitial.equals(other.middleInitial))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
	
}
