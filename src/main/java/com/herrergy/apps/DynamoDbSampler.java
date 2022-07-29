package com.herrergy.apps;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class DynamoDbSampler {

    public void runSamples() {
        Region region = Region.US_EAST_2;
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(region).build();

        for (int i = 1; i < 10; i++) {
            ListTablesResponse response = dynamoDbClient.listTables();

            System.out.println("Response: " + response.toString());
        }


        dynamoDbClient.close();
    }
}
