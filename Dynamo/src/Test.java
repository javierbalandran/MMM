import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import minimed.data.Account;
import minimed.data.BloodType;
import minimed.data.Gender;
import minimed.data.User;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Test {

	static AmazonDynamoDBClient dynamoDB;
	
	public static void main(String[] args) throws IOException {
	    AWSCredentialsProvider credentialProvider = new ClasspathPropertiesFileCredentialsProvider();
	    
	    dynamoDB = new AmazonDynamoDBClient(credentialProvider);
	    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	    dynamoDB.setRegion(usWest2);
	    
	    String bucketName = "mini-med";
	    String username = "test2@blah.com"; 
	    String keyName = username + "-userData.txt";
	    
	    Account account = new Account(username);
	    User user = new User("Jim", "P", "Bob","4/1/1992",BloodType.B_POSITIVE, Gender.MALE);
	    
	    DynamoDBMapper dynMapper = new DynamoDBMapper(dynamoDB, credentialProvider);

	    // S3 region can be specified, but is optional
	    S3Link s3UserLink = dynMapper.createS3Link(bucketName, keyName);
	    account.setUserData(s3UserLink);
	    dynMapper.save(account);
	    
	    File userFile = File.createTempFile("user",".txt");
	    userFile.deleteOnExit();
	    
	    BufferedWriter userWriter = new BufferedWriter(new FileWriter(userFile));
	    userWriter.write(user.getJSONString());
	    userWriter.flush();
	    userWriter.close();
	    
	    //s3UserLink.uploadFrom(userFile); 
	    
	    AmazonS3Client s3Client = s3UserLink.getAmazonS3Client();
	    
	    PutObjectRequest putRequest = new PutObjectRequest(
                bucketName, keyName, userFile);

		//Request server-side encryption.
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setServerSideEncryption(
		ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);     
		putRequest.setMetadata(objectMetadata);
		
		PutObjectResult response = s3Client.putObject(putRequest);
	    
//        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
//        item.put("username", new AttributeValue(username));
//        PutItemRequest putItemRequest = new PutItemRequest("Accounts", item);
//        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
//        System.out.println("Result: " + putItemResult);
	    
        Map<String, AttributeValue> key = new HashMap<String, AttributeValue>();
        key.put("username", new AttributeValue(username));
        GetItemRequest getRequest = new GetItemRequest("Accounts", key);
        GetItemResult getResult = dynamoDB.getItem(getRequest);
        System.out.println(getResult.getItem().get("username").getS());
	}

	
}
