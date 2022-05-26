package com.store.cloud.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

public class DynamodbConfig {
    public static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    public static DynamoDB dynamoDB = new DynamoDB(client);
    public static String tableName = "Products";

    public static void createItems() {
        Table table = dynamoDB.getTable(tableName);
        try {
            Item item = new Item().withPrimaryKey("id", 2)
                    .withString("name", "salt")
                    .withString("price","102")
                    .withString("pictureUrl", "http://test.example.com");

            table.putItem(item);
        } catch (Exception e) {
            System.out.println("Creation of item has failed " + e.getMessage());
        }
    }
}