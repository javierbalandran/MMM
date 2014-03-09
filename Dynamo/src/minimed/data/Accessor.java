package minimed.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.S3Link;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Accessor {
	
	private static final String bucketName = "mini-med";
	
	private static AmazonDynamoDBClient dynamoDB;

	//type being what field, eg: -userData.txt, -contact.txt...
	public static void putToS3(S3Link link, List<String> data, String username, String type) throws IOException {
	    AWSCredentialsProvider credentialProvider = new ClasspathPropertiesFileCredentialsProvider();
	    
	    dynamoDB = new AmazonDynamoDBClient(credentialProvider);
	    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	    dynamoDB.setRegion(usWest2);
		
		String list = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			list = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("BAD JSON");
		}
		
	    DynamoDBMapper dynMapper = new DynamoDBMapper(dynamoDB, credentialProvider);

	    S3Link s3UserLink = dynMapper.createS3Link(bucketName, username + type);
		
		File dataFile = File.createTempFile("Put " + type, ".txt");
	    dataFile.deleteOnExit();
	    
	    BufferedWriter dataWriter = new BufferedWriter(new FileWriter(dataFile));
	    dataWriter.write(list);
	    dataWriter.flush();
	    dataWriter.close();
	    
	    AmazonS3Client s3Client = s3UserLink.getAmazonS3Client();
	    
	    PutObjectRequest putRequest = new PutObjectRequest(
                bucketName, username + type, dataFile);

		//Request server-side encryption.
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setServerSideEncryption(
		ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);     
		putRequest.setMetadata(objectMetadata);
		
		PutObjectResult response = s3Client.putObject(putRequest);
	    
	    //s3UserLink.uploadFrom(dataFile); 
	}
	
	public List<String> getFromS3(S3Link link, String username, String type) throws IOException {
	    AWSCredentialsProvider credentialProvider = new ClasspathPropertiesFileCredentialsProvider();
	    
	    dynamoDB = new AmazonDynamoDBClient(credentialProvider);
	    Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	    dynamoDB.setRegion(usWest2);
	    
		File dataFile = File.createTempFile("Get " + type, ".txt");
	    dataFile.deleteOnExit();
		
	    DynamoDBMapper dynMapper = new DynamoDBMapper(dynamoDB, credentialProvider);
	    S3Link s3UserLink = dynMapper.createS3Link("mini-med", username + type);
	    s3UserLink.downloadTo(dataFile);
	    
	    BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));
	    String jsonData = dataReader.readLine();
	    
		ObjectMapper mapper = new ObjectMapper();
		List<String> values = new ArrayList<String>();
		
		try {
			values = mapper.readValue(jsonData, values.getClass());
		} catch (IOException e) {
			throw new RuntimeException("BAD JSON");
		}
		
		return values;
	}
	
}
