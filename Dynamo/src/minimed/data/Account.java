package minimed.data;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;

@DynamoDBTable(tableName = "Accounts")
public class Account {

	private Accessor dynamoAccessor;
	
	//Fields in Dynamo
	private String username;
	private S3Link userData;
	private S3Link contactData;
	private S3Link allergyData;
	private S3Link medicationData;
	private S3Link historyData;
	private S3Link imageData;
	
	//From s3 links
	private User user;
	private List<Allergy> allergies;
	private List<Contact> contacts;
	private List<Medication> medication;
	private List<MedicalHistory> medHistory;
	//private List<Image> images;
	
	public Account initAccount(String username) {
		dynamoAccessor = new Accessor();
		
		DynamoDBMapper dynMapper = dynamoAccessor.getDynMapper();
		
	    Account account = new Account(username);
	    
	    S3Link s3UserLink = dynMapper.createS3Link(FieldNames.S3BucketKeys.bucket, 
	    		username + FieldNames.S3BucketKeys.userDataFormat);
	    S3Link s3ContactLink = dynMapper.createS3Link(FieldNames.S3BucketKeys.bucket, 
	    		username + FieldNames.S3BucketKeys.contactDataFormat);
	    S3Link s3AllergyLink = dynMapper.createS3Link(FieldNames.S3BucketKeys.bucket, 
	    		username + FieldNames.S3BucketKeys.allergyDataFormat);
	    S3Link s3MedicationLink = dynMapper.createS3Link(FieldNames.S3BucketKeys.bucket, 
	    		username + FieldNames.S3BucketKeys.medicationDataFormat);
	    S3Link s3HistoryLink = dynMapper.createS3Link(FieldNames.S3BucketKeys.bucket, 
	    		username + FieldNames.S3BucketKeys.historyDataFormat);

	    account.setUserData(s3UserLink);
	    account.setAllergyData(s3AllergyLink);
	    account.setContactData(s3ContactLink);
	    account.setHistoryData(s3HistoryLink);
	    account.setMedicationData(s3MedicationLink);

	    dynMapper.save(account);
	    
	    return account;
	}
	
	public Account(String username) {
		this.username = username;
	}
	
    @DynamoDBHashKey
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public S3Link getUserData() {
		return userData;
	}

	public void setUserData(S3Link userData) {
		this.userData = userData;
	}

	public S3Link getContactData() {
		return contactData;
	}

	public void setContactData(S3Link contactData) {
		this.contactData = contactData;
	}

	public S3Link getAllergyData() {
		return allergyData;
	}

	public void setAllergyData(S3Link allergyData) {
		this.allergyData = allergyData;
	}

	public S3Link getMedicationData() {
		return medicationData;
	}

	public void setMedicationData(S3Link medicationData) {
		this.medicationData = medicationData;
	}

	public S3Link getHistoryData() {
		return historyData;
	}

	public void setHistoryData(S3Link historyData) {
		this.historyData = historyData;
	}

	public User getUser() {
		if (user == null) {
			refreshUser();
		}
		return user;
	}

	public List<Allergy> getAllergies() {
		return allergies;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public List<Medication> getMedication() {
		return medication;
	}

	public List<MedicalHistory> getMedHistory() {
		return medHistory;
	}

	public boolean checkPassword(String username, String passwordCheck) {
		String password = null;
		Account account = initAccount(username);
		return password.equals(passwordCheck);
	}
    
	public void refreshUser() {
	    List<String> returnList = dynamoAccessor.getFromS3(userData, username, FieldNames.S3BucketKeys.userDataFormat);
	    user = new User(returnList.get(0));
	}
	
	public void updateUser(User user) {
		List<String> jsonList = new ArrayList<String>();
		
		jsonList.add(user.getJSONString());
		
		dynamoAccessor.putToS3(userData, jsonList, username, FieldNames.S3BucketKeys.userDataFormat);
	}
	
	public void refreshContacts() {
		List<Contact> data = new ArrayList<Contact>();
	    List<String> returnList = dynamoAccessor.getFromS3(contactData, username, FieldNames.S3BucketKeys.contactDataFormat);
	    
	    for (String str : returnList) {
	    	data.add(new Contact(str));
	    }
	    contacts = data; 
	}
	
	public void updateContacts(List<Contact> newContacts) {
		List<String> jsonList = new ArrayList<String>();
		
		for(Contact cont : newContacts) {
			jsonList.add(cont.getJSONString());
		}
		
		dynamoAccessor.putToS3(contactData, jsonList, username, FieldNames.S3BucketKeys.contactDataFormat);
	}
	
	public void refreshAllergies() {
		List<Allergy> data = new ArrayList<Allergy>();
	    List<String> returnList = dynamoAccessor.getFromS3(allergyData, username, FieldNames.S3BucketKeys.allergyDataFormat);
	    
	    for (String str : returnList) {
	    	data.add(new Allergy(str));
	    }
	    allergies = data; 
	}
	
	public void updateAllergies(List<Allergy> newAllergies) {
		List<String> jsonList = new ArrayList<String>();
		
		for(Allergy data : newAllergies) {
			jsonList.add(data.getJSONString());
		}
		
		dynamoAccessor.putToS3(allergyData, jsonList, username, FieldNames.S3BucketKeys.allergyDataFormat);
	}
	
	public void refreshMedications() {
		List<Medication> data = new ArrayList<Medication>();
	    List<String> returnList = dynamoAccessor.getFromS3(medicationData, username, FieldNames.S3BucketKeys.medicationDataFormat);
	    
	    for (String str : returnList) {
	    	data.add(new Medication(str));
	    }
	    medication = data; 
	}
	
	public void refreshHistories() {
		List<MedicalHistory> data = new ArrayList<MedicalHistory>();
	    List<String> returnList = dynamoAccessor.getFromS3(historyData, username, FieldNames.S3BucketKeys.historyDataFormat);
	    
	    for (String str : returnList) {
	    	data.add(new MedicalHistory(str));
	    }
	    medHistory = data; 
	}
	
}
