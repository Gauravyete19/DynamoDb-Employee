package com.gaurav.dynamodbemployee.config;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.eventsources.DynamoEventSource;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.StreamViewType;

public class EmployeePortalStack extends Stack {
    public EmployeePortalStack(final Construct scope, final String id) {
        super(scope, id);

        // DynamoDB table definition
        Table employeeTable = Table.Builder.create(this, "EmployeeTable")
            .tableName("Employee")
            .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
            .stream(StreamViewType.NEW_AND_OLD_IMAGES)
            .build();

        // Lambda function
        Function employeeFunction = Function.Builder.create(this, "EmployeeFunction")
            .runtime(Runtime.JAVA_11)
            .code(Code.fromAsset("target/DynamodbEmployeeApplication.jar"))
            .handler("com.gaurav.dynamodbemployee.config.MyLambdaFunction::handleRequest")
            .environment(Map.of("TABLE_NAME", employeeTable.getTableName()))
            .build();

        // Grant the Lambda function read access to the DynamoDB table
        employeeTable.grantReadData(employeeFunction);

        // Configure event source mapping
        employeeFunction.addEventSource(new DynamoEventSource(employeeTable, DynamoEventSourceProps.builder().build()));
    }
}
