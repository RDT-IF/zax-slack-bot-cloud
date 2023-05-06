package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

class UrlVerificationHandler {
    APIGatewayProxyResponseEvent handle(UrlVerificationEvent event) {
        return StandardResponses.RESPONSE_FOR_VALID_REQUEST;
    }
}
