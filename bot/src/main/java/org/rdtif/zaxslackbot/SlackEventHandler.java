package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventHandler {
    private final ChallengeValidator challengeValidator;

    @Inject
    SlackEventHandler(ChallengeValidator challengeValidator) {
        this.challengeValidator = challengeValidator;
    }

    APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        if (slackEvent == null || slackEvent.getType() == null) {
            return RESPONSE_FOR_INVALID_REQUEST;
        }

        if (slackEvent.getChallenge() == null) {
            return RESPONSE_FOR_INVALID_AUTHENTICATION;
        }
        if (challengeValidator.valid(slackEvent.getChallenge())) {
            return RESPONSE_FOR_VALID_REQUEST;
        }
        return RESPONSE_FOR_INVALID_AUTHENTICATION;
    }

    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_VALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(200);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_REQUEST = new APIGatewayProxyResponseEvent().withStatusCode(400);
    private static final APIGatewayProxyResponseEvent RESPONSE_FOR_INVALID_AUTHENTICATION = new APIGatewayProxyResponseEvent().withStatusCode(401);
}
