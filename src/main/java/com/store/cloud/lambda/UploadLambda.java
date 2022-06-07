package com.store.cloud.lambda;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.store.cloud.dynamodb.DynamodbConfig;
import com.store.cloud.model.Product;
import org.json.JSONObject;
import org.json.JSONTokener;

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
import java.util.Map;

public class UploadLambda implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)))) {
            HashMap event = gson.fromJson(reader, HashMap.class);
            Product product = gson.fromJson(event.get("body").toString(), Product.class);

            DynamodbConfig.createItems(product);

            JSONObject obj = new JSONObject();
            JSONObject obj2 = new JSONObject();
            obj2.put("Content-Type", "application/json");
            obj.put("statusCode", 200);
            obj.put("headers", obj2);
            obj.put("body", gson.toJson(product));

            writer.write(obj.toString());
            writer.close();

            if (writer.checkError()) {
                System.out.println("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            System.out.println(exception);
        }
    }
}
