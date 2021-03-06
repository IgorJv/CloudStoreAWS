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

import static com.store.cloud.constants.Constants.BODY;
import static com.store.cloud.constants.Constants.LAMBDA_ERROR_MESSAGE;
import static com.store.cloud.utils.LambdaUtils.generateResponse;

public class UploadLambda implements RequestStreamHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)))) {
            HashMap event = gson.fromJson(reader, HashMap.class);
            Product product = gson.fromJson(event.get(BODY).toString(), Product.class);

            DynamodbConfig.createItems(product);

            writer.write(generateResponse(gson.toJson(product)).toString());
            writer.close();

            if (writer.checkError()) {
                System.out.println(LAMBDA_ERROR_MESSAGE);
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            System.out.println(exception);
        }
    }
}
