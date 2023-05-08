package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

interface HttpConstants {
    APIGatewayProxyResponseEvent RESPONSE_OK = new APIGatewayProxyResponseEvent().withStatusCode(200);
    APIGatewayProxyResponseEvent RESPONSE_BAD_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(400);
    APIGatewayProxyResponseEvent RESPONSE_UNAUTHENTICATED = new APIGatewayProxyResponseEvent().withStatusCode(401);
    int HTTP_OK = 200;
    int HTTP_BAD_REQUEST = 400;
    int HTTP_UNAUTHENTICATED = 401;
}
