package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventHandler {
    private final SlackEventDispatcher dispatcher;
    private final SlackEventValidator validator;

    @Inject
    SlackEventHandler(SlackEventValidator eventValidator, SlackEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        this.validator = eventValidator;
    }

    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        int statusCode = validator.validate(apiEvent);
        if (statusCode == 200) {
            return dispatcher.dispatch(slackEvent, apiEvent.getBody());
        }
        return new APIGatewayProxyResponseEvent().withStatusCode(statusCode);
    }
}
