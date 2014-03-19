package minimed.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import minimed.data.Accessor;
import minimed.data.Account;
import minimed.data.Allergy;
import minimed.data.AllergyType;
import minimed.data.BloodType;
import minimed.data.Contact;
import minimed.data.ContactType;
import minimed.data.FieldNames;
import minimed.data.Gender;
import minimed.data.MedicalHistory;
import minimed.data.Medication;
import minimed.data.User;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;

public class DynamoTest {

	private static Accessor dynamoAccessor;
	private static DynamoDBMapper dynMapper;
	private static Account account;
	private static String username = "blah@faketest.com";
	private static String bucketName = "mini-med";
	
	@BeforeClass
	public static void beforeClass() {
		dynamoAccessor = new Accessor();
		
		dynMapper = dynamoAccessor.getDynMapper();
		
	    account = new Account(username);
	    
	    S3Link s3UserLink = dynMapper.createS3Link(bucketName, 
	    		username + FieldNames.S3BucketKeys.userDataFormat);
	    S3Link s3ContactLink = dynMapper.createS3Link(bucketName, 
	    		username + FieldNames.S3BucketKeys.contactDataFormat);
	    S3Link s3AllergyLink = dynMapper.createS3Link(bucketName, 
	    		username + FieldNames.S3BucketKeys.allergyDataFormat);
	    S3Link s3MedicationLink = dynMapper.createS3Link(bucketName, 
	    		username + FieldNames.S3BucketKeys.medicationDataFormat);
	    S3Link s3HistoryLink = dynMapper.createS3Link(bucketName, 
	    		username + FieldNames.S3BucketKeys.historyDataFormat);

	    account.setUserData(s3UserLink);
	    account.setAllergyData(s3AllergyLink);
	    account.setContactData(s3ContactLink);
	    account.setHistoryData(s3HistoryLink);
	    account.setMedicationData(s3MedicationLink);

	    dynMapper.save(account);
	}
	
	@Test
	public void UserTest() throws IOException {
		User before = new User("John", "A", "Doe", "4/14/1992", BloodType.A_NEGATIVE, Gender.MALE, "password1");
		
	    S3Link s3Link = account.getUserData();
	    
	    List<String> jsonList = new ArrayList<String>();
	    jsonList.add(before.getJSONString());
	    
	    dynamoAccessor.putToS3(s3Link, jsonList, username, FieldNames.S3BucketKeys.userDataFormat);
	    
	    List<String> returnList = dynamoAccessor.getFromS3(s3Link, username, FieldNames.S3BucketKeys.userDataFormat);
	    
	    User after = new User(returnList.get(0));
	    
	    assertTrue("User not equal", before.equals(after));
	}
	
	@Test
	public void ContactTest() throws IOException {
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(new Contact(ContactType.EMERGENCY, "Jim Bob", "Brother", "408-555-1234",
				"408-555-9876", "805-555-5555"));
		contacts.add(new Contact(ContactType.EMERGENCY, "Jane Jill", "Mother", "408-555-1234",
				null, null));
		
	    S3Link s3Link = account.getContactData();
	    
	    List<String> jsonList = new ArrayList<String>();
	    
	    for (Contact contact : contacts) {
	    	jsonList.add(contact.getJSONString());
	    }
	    
	    dynamoAccessor.putToS3(s3Link, jsonList, username, FieldNames.S3BucketKeys.contactDataFormat);
	    
	    List<String> returnList = dynamoAccessor.getFromS3(s3Link, username, FieldNames.S3BucketKeys.contactDataFormat);
	    
	    for (int i = 0; i < contacts.size(); i++) {
	    	Contact after = new Contact(returnList.get(i));
		    assertTrue("Contact "+i+" not equal", after.equals(contacts.get(i)));
	    }
	}
	
	@Test
	public void AllergyTest() throws IOException {
		List<Allergy> allergies = new ArrayList<Allergy>();
		allergies.add(new Allergy("Bee", AllergyType.ENVIRONMENT, "Highly allergic, have an epi-pen"));
		allergies.add(new Allergy("Pennicilin", AllergyType.DRUG, "Break out in hives"));
		
	    S3Link s3Link = account.getAllergyData();
	    
	    List<String> jsonList = new ArrayList<String>();
	    for (Allergy allergy : allergies) {
	    	jsonList.add(allergy.getJSONString());
	    }
	    
	    dynamoAccessor.putToS3(s3Link, jsonList, username, FieldNames.S3BucketKeys.allergyDataFormat);
	    
	    List<String> returnList = dynamoAccessor.getFromS3(s3Link, username, FieldNames.S3BucketKeys.allergyDataFormat);
	    
	    for (int i = 0; i < allergies.size(); i++) {
	    	Allergy after = new Allergy(returnList.get(i));
		    assertTrue("User not equal", after.equals(allergies.get(i)));
	    }
	}
	
	@Test
	public void MedicationTest() throws IOException {
		List<Medication> medications = new ArrayList<Medication>();
		medications.add(new Medication("Advil", "Pain-killer", "OTC", 1, 7));
		medications.add(new Medication("Advil", "Pain-killer", "OTC", 12, 1));
		
	    S3Link s3Link = account.getMedicationData();
	    
	    List<String> jsonList = new ArrayList<String>();
	    for(Medication med : medications) {
	    	jsonList.add(med.getJSONString());
	    }
	    
	    dynamoAccessor.putToS3(s3Link, jsonList, username, FieldNames.S3BucketKeys.medicationDataFormat);
	    
	    List<String> returnList = dynamoAccessor.getFromS3(s3Link, username, FieldNames.S3BucketKeys.medicationDataFormat);
	    
	    for (int i = 0; i < medications.size(); i++) {
	    	Medication after = new Medication(returnList.get(i));
		    assertTrue("User not equal", after.equals(medications.get(i)));
	    }
	}
	
	@Test
	public void MedicalHistoryTest() throws IOException {
		List<MedicalHistory> histories = new ArrayList<MedicalHistory>();
		histories.add(new MedicalHistory("Doctor's visit", "Normal Checkup, everything went fine", "1/12/2011"));
		
	    S3Link s3Link = account.getHistoryData();
	    
	    List<String> jsonList = new ArrayList<String>();
	    
	    for(MedicalHistory history : histories) {
	    	jsonList.add(history.getJSONString());
	    }
	    
	    dynamoAccessor.putToS3(s3Link, jsonList, username, FieldNames.S3BucketKeys.historyDataFormat);
	    
	    List<String> returnList = dynamoAccessor.getFromS3(s3Link, username, FieldNames.S3BucketKeys.historyDataFormat);
	    
	    for (int i = 0; i < histories.size(); i++) {
	    	MedicalHistory after = new MedicalHistory(returnList.get(i));
		    assertTrue("User not equal", after.equals(histories.get(i)));
	    }
	}

}
