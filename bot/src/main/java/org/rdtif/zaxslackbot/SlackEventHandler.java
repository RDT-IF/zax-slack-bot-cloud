package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventHandler {
    private final SlackTimestampValidator slackTimestampValidator;
    private final SlackSignatureValidator slackSignatureValidator;
    private final SlackEventDispatcher dispatcher;

    @Inject
    SlackEventHandler(SlackTimestampValidator slackTimestampValidator, SlackSignatureValidator slackSignatureValidator, SlackEventDispatcher dispatcher) {
        this.slackTimestampValidator = slackTimestampValidator;
        this.slackSignatureValidator = slackSignatureValidator;
        this.dispatcher = dispatcher;
    }

    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        String timestamp = apiEvent.getHeaders().get("X-Slack-Request-Timestamp");
        String signature = apiEvent.getHeaders().get("X-Slack-Signature");
        if (slackEvent == null || signature == null || timestamp == null || slackEvent.getType() == null) {
            return StandardResponses.RESPONSE_FOR_INVALID_REQUEST;
        }
        if (slackTimestampValidator.validate(timestamp) && slackSignatureValidator.validate(signature, timestamp, apiEvent.getBody())) {
            return dispatcher.dispatch(slackEvent, apiEvent.getBody());
        }
        return StandardResponses.RESPONSE_FOR_INVALID_AUTHENTICATION;
    }
}
