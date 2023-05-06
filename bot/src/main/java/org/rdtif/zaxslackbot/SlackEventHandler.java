package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventHandler {
    private final SlackTimestampValidator slackTimestampValidator;
    private final SlackSignatureValidator slackSignatureValidator;
    private final SlackEventDispatcher dispatcher;
    private final SlackEventValidator validator;

    @Inject
    SlackEventHandler(SlackTimestampValidator slackTimestampValidator, SlackSignatureValidator slackSignatureValidator, SlackEventDispatcher dispatcher, SlackEventValidator eventValidator) {
        this.slackTimestampValidator = slackTimestampValidator;
        this.slackSignatureValidator = slackSignatureValidator;
        this.dispatcher = dispatcher;
        this.validator = eventValidator;
    }

    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        if (validator.validate(apiEvent)) {
            return dispatcher.dispatch(slackEvent, apiEvent.getBody());
        }
        return StandardResponses.RESPONSE_FOR_INVALID_AUTHENTICATION;
    }
}
