package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

class SlackEventHandler {
    private final ChallengeValidator challengeValidator;

    //@Inject
    SlackEventHandler(ChallengeValidator challengeValidator) {
        this.challengeValidator = challengeValidator;
    }

    APIGatewayProxyResponseEvent handle(String payload) {
        SlackEvent event = new Gson().fromJson(payload, SlackEvent.class);
        if (event == null || event.getType() == null) {
            return RESPONSE_FOR_INVALID_REQUEST;
        }

        if (event.getChallenge() == null) {
            return RESPONSE_FOR_INVALID_AUTHENTICATION;
        }
        if (challengeValidator.valid(event.getChallenge())) {
            return RESPONSE_FOR_VALID_REQUEST;
        }
        return RESPONSE_FOR_INVALID_AUTHENTICATION;
    }

    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_VALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(200);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(400);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_AUTHENTICATION = new APIGatewayProxyResponseEvent().withStatusCode(401);
}
