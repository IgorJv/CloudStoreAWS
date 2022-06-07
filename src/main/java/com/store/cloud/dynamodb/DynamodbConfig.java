package com.store.cloud.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.store.cloud.model.Product;

public class DynamodbConfig {
    public static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    public static DynamoDB dynamoDB = new DynamoDB(client);
    private static final String tableName = "Products";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PICTURE_URL = "pictureUrl";

    public static void createItems(Product product) {
        Table table = dynamoDB.getTable(tableName);
        try {
            Item item = new Item().withPrimaryKey(ID, product.getId())
                    .withString(NAME, product.getName())
                    .withString(PRICE, product.getPrice())
                    .withString(PICTURE_URL, product.getPictureUrl());

            table.putItem(item);
        } catch (Exception e) {
            System.out.println("Creation of item has failed " + e.getMessage());
        }
    }

    public static void updateItems(Product product) {
        Table table = dynamoDB.getTable(tableName);

        try {
            UpdateItemSpec updateNameItemSpec = new UpdateItemSpec().withPrimaryKey(ID, product.getId())
                    .withAttributeUpdate(new AttributeUpdate(NAME).put(product.getName()))
                    .withReturnValues(ReturnValue.ALL_NEW);
            UpdateItemSpec updatePriceItemSpec = new UpdateItemSpec().withPrimaryKey(ID, product.getId())
                    .withAttributeUpdate(new AttributeUpdate(PRICE).put(product.getPrice()))
                    .withReturnValues(ReturnValue.ALL_NEW);
            UpdateItemSpec updatePictureUrlItemSpec = new UpdateItemSpec().withPrimaryKey(ID, product.getId())
                    .withAttributeUpdate(new AttributeUpdate(PICTURE_URL).put(product.getPictureUrl()))
                    .withReturnValues(ReturnValue.ALL_NEW);
            UpdateItemOutcome updateItemOutcome = table.updateItem(updateNameItemSpec);
            table.updateItem(updatePriceItemSpec);
            table.updateItem(updatePictureUrlItemSpec);

            System.out.println("Item updated.");
            System.out.println(updateItemOutcome.getItem().toJSON());
        } catch (Exception e) {
            System.err.println("Failed to update multiple attributes in " + tableName);
            System.err.println(e.getMessage());
        }
    }
}