package com.store.cloud.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.store.cloud.dynamodb.DynamodbConfig;
import com.store.cloud.model.Product;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class UpdateLambda implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.US_ASCII)))) {
            HashMap event = gson.fromJson(reader, HashMap.class);
            Product product = gson.fromJson(event.get("body").toString(), Product.class);

            DynamodbConfig.updateItems(product);

            System.out.println("response: " + gson.toJson(event));

            writer.write(gson.toJson(event));
            if (writer.checkError()) {
                System.out.println("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            System.out.println(exception);
        }
    }
}
