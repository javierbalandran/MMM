package minimed.data;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class AllergyAccessor extends DynamoAccessor {

	private static final String TABLE_NAME = "Allergies";
	private static final String USER_KEY = "userId";

	public static final String NAME = "name";
	public static final String DETAILS = "details";
	
	public AllergyAccessor() {
		super();
	}
	
	public Allergy getAllergy(String username) {
		Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
		key.put("userId", new AttributeValue(username));
		
		GetItemRequest getItemRequest = new GetItemRequest(TABLE_NAME, key);
		GetItemResult getResult = dynamoDB.getItem(getItemRequest);
		return new Allergy(getResult.getItem());
	}
	
	public void addAllergy(Allergy allergy) {
        Map<String, AttributeValue> item = newItem(allergy);
        PutItemRequest putItemRequest = new PutItemRequest(TABLE_NAME, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
        System.out.println("Result: " + putItemResult);
	}
	
    private Map<String, AttributeValue> newItem(Allergy allergy) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put(DETAILS, new AttributeValue(allergy.getDetails()));

        return item;
    }
	
}
