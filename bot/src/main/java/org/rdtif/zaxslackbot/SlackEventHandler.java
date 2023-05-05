package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventHandler {
    private final SlackTimestampValidator slackTimestampValidator;

    @Inject
    SlackEventHandler(SlackTimestampValidator slackTimestampValidator) {
        this.slackTimestampValidator = slackTimestampValidator;
    }

    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        String timestamp = apiEvent.getHeaders().get("X-Slack-Request-Timestamp");
        if (slackEvent == null || timestamp == null || slackEvent.getType() == null) {
            return RESPONSE_FOR_INVALID_REQUEST;
        }
        if (slackTimestampValidator.validate(timestamp)) {
            return RESPONSE_FOR_VALID_REQUEST;
        }
        return RESPONSE_FOR_INVALID_AUTHENTICATION;
    }

    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_VALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(200);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(400);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_AUTHENTICATION = new APIGatewayProxyResponseEvent().withStatusCode(401);
}
