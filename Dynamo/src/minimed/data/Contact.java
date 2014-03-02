package minimed.data;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class Contact {

	private ContactType type;
	private String firstName;
	private String lastName;
	private String relation;
	private String phoneNumber;
	
	public Contact(Map<String, AttributeValue> item) {
		type = ContactType.valueOf((item.get(ContactAccessor.TYPE)).getS());
		firstName = (item.get(ContactAccessor.FIRST_NAME)).getS();
		lastName = (item.get(ContactAccessor.LAST_NAME)).getS();
		relation = (item.get(ContactAccessor.RELATION)).getS();
		phoneNumber = (item.get(ContactAccessor.PHONE_NUMBER)).getS();
	}
	
	public Contact(ContactType type, String firstName, String lastName,
			String relation, String phoneNumber) {
		Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
	    Matcher matcher = pattern.matcher(phoneNumber);
	    
	    if (!matcher.matches()) {
	    	//phone number doesn't match XXX-XXXXXXX
	    }
		
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.relation = relation;
		this.phoneNumber = phoneNumber;
	}
	
	public ContactType getType() {
		return type;
	}
	public void setType(ContactType type) {
		this.type = type;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Contact [type=" + type + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", relation=" + relation
				+ ", phoneNumber=" + phoneNumber + "]";
	}	
	
}
