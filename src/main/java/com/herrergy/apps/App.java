package com.herrergy.apps;

public class App
{
    public static void main( String[] args ) {
       AppSetup.setupOpenTelemetry();

        DynamoDbSampler dynamoDbSampler = new DynamoDbSampler();
        dynamoDbSampler.runSamples();

        S3Sampler s3Sampler = new S3Sampler();
        s3Sampler.runSampler();
    }
}
