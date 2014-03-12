package minimed.data;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class ContactAccessor extends DynamoAccessor {

	public static final String KEY = "userId";
	
	public static final String TYPE = "type";
	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String RELATION = "relation";
	public static final String PHONE_NUMBER = "phoneNumber";
	
	public static final String TABLE_NAME = "Contacts";
	
	public ContactAccessor() {
		super();
	}
	
	public Contact getContact(String username) {
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put(KEY, new AttributeValue(username));
		
		GetItemRequest getItemRequest = new GetItemRequest(TABLE_NAME, key);
		GetItemResult getResult = dynamoDB.getItem(getItemRequest);
		return new Contact(getResult.getItem());
	}
	
	public void addContact(Contact contact, String userId) {
        Map<String, AttributeValue> item = newItem(contact);
        item.put(KEY, new AttributeValue(userId));
        PutItemRequest putItemRequest = new PutItemRequest(TABLE_NAME, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
	}
	
    private Map<String, AttributeValue> newItem(Contact contact) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put(TYPE, new AttributeValue(contact.getType().name()));
        item.put(FIRST_NAME, new AttributeValue(contact.getFirstName()));
        item.put(LAST_NAME, new AttributeValue(contact.getLastName()));
        item.put(RELATION, new AttributeValue(contact.getRelation()));
        item.put(PHONE_NUMBER, new AttributeValue(contact.getPhoneNumber()));

        return item;
    }
	
}
