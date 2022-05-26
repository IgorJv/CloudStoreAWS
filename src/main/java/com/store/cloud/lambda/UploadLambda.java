package com.store.cloud.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.store.cloud.dynamodb.DynamodbConfig;

public class UploadLambda implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object o, Context context) {
        DynamodbConfig.createItems();
        System.out.println("Hello from lambda!");
        return 201;
    }
}
