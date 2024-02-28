package com.gaurav.dynamodbemployee.config;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class MyLambdaFunction implements RequestHandler<DynamodbEvent, Void> {
    @Override
    public Void handleRequest(DynamodbEvent event, Context context) {
        // Retrieve DynamoDB client
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        // Iterate through records in the event
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            if (record.getEventName().equals("INSERT")) {
                // Fetch the item from DynamoDB
                Item item = dynamoDB.getTable(System.getenv("TABLE_NAME")).getItem("id", record.getDynamodb().getNewImage().get("id").getS());
                // Process the item or perform any desired action
                System.out.println("Retrieved item: " + item.toJSON());
            }
        }
        return null;
    }
}
