package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

class UrlVerificationHandler {
    APIGatewayProxyResponseEvent handle(UrlVerificationEvent event) {
        return HttpConstants.RESPONSE_OK;
    }
}
