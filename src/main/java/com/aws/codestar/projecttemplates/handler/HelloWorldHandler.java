package com.aws.codestar.projecttemplates.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import com.aws.codestar.projecttemplates.GatewayResponse;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import java.net.URL;
import java.net.HttpURLConnection;

/**
 * Handler for requests to Lambda function.
 */
public class HelloWorldHandler implements RequestHandler<Object, Object> {

    public Object handleRequest(final Object input, final Context context) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Boolean canConnectToPublicURLs = this.checkOutgoingConnectivity(context.getLogger());
        JSONObject contentObject = new JSONObject();
        contentObject.put("Output", "Hello World!");
        contentObject.put("canConnectToPublicURLs", canConnectToPublicURLs);
        return new GatewayResponse(contentObject.toString(), headers, 200);
    }
    
    private boolean checkOutgoingConnectivity(LambdaLogger logger) {
        try {
            URL urlObj = new URL("https://jsonplaceholder.typicode.com/users");
            HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
            int responseCode = httpCon.getResponseCode();
            logger.log("response code from URL check in checkOutgoingConnectivity: " + responseCode);
            return (responseCode > 0) ? true : false;
        } catch (Exception e) {
            logger.log(e.getMessage());
            return false;
        }
    }
}
