package com.herrergy.apps;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3Sampler {
    public void runSampler() {
        S3Client s3Client = S3Client.builder().region(Region.US_EAST_2).build();

        for (int i = 1; i < 10; i++) {
            ListBucketsResponse response = s3Client.listBuckets();

            System.out.println(response);
        }

        s3Client.close();
    }
}
