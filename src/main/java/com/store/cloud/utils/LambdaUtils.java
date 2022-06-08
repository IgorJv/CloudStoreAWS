package com.store.cloud.utils;

import org.json.JSONObject;

import static com.store.cloud.constants.Constants.APPLICATION_JSON;
import static com.store.cloud.constants.Constants.BODY;
import static com.store.cloud.constants.Constants.CONTENT_TYPE;
import static com.store.cloud.constants.Constants.HEADERS;
import static com.store.cloud.constants.Constants.STATUS_CODE;
import static com.store.cloud.constants.Constants.STATUS_CODE_200;

public class LambdaUtils {
    public static JSONObject generateResponse(String body) {
        JSONObject jsonObject = new JSONObject();
        JSONObject headers = new JSONObject();
        headers.put(CONTENT_TYPE, APPLICATION_JSON);
        jsonObject.put(STATUS_CODE, STATUS_CODE_200);
        jsonObject.put(HEADERS, headers);
        jsonObject.put(BODY, body);

        return jsonObject;
    }
}
