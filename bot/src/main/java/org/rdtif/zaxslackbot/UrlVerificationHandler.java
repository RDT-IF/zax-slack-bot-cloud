package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import java.util.Collections;

class UrlVerificationHandler extends SlackEventHandler<UrlVerificationEvent> {
    APIGatewayProxyResponseEvent handle(UrlVerificationEvent event) {
        return HttpConstants.RESPONSE_OK.withBody(event.getChallenge()).withHeaders(Collections.singletonMap("Content-Type", "text/plain"));
    }
}
