package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

interface StandardResponses {
    APIGatewayProxyResponseEvent RESPONSE_FOR_VALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(200);
    APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(400);
    APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_AUTHENTICATION = new APIGatewayProxyResponseEvent().withStatusCode(401);
}
