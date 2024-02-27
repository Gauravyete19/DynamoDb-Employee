package com.gaurav.dynamodbemployee.config;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class MyLambdaFunction implements RequestHandler<S3Event, String> {

	private AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
	private DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
	private String tableName = "MyDynamoDBTable";

	@Override
	public String handleRequest(S3Event s3Event, Context context) {
		// Process S3 event and retrieve data

		// Example: Get object key from S3 event
		String objectKey = s3Event.getRecords().get(0).getS3().getObject().getKey();

		// Example: Insert data into DynamoDB table
		Table table = dynamoDB.getTable("Employee");
		Item item = new Item().withPrimaryKey("id", objectKey).withString("id", "1");
		table.putItem(item);

		return "Data processed successfully";
	}
}