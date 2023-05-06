package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventValidator {
    private final SlackTimestampValidator timestampValidator;
    private final SlackSignatureValidator signatureValidator;

    @Inject
    public SlackEventValidator(SlackTimestampValidator timestampValidator, SlackSignatureValidator signatureValidator) {
        this.timestampValidator = timestampValidator;
        this.signatureValidator = signatureValidator;
    }

    int validate(APIGatewayProxyRequestEvent apiEvent) {
        SlackEvent slackEvent = new Gson().fromJson(apiEvent.getBody(), SlackEvent.class);
        String timestamp = apiEvent.getHeaders().get("X-Slack-Request-Timestamp");
        String signature = apiEvent.getHeaders().get("X-Slack-Signature");
        if (slackEvent == null || timestamp == null || slackEvent.getType() == null) {
            return HttpConstants.HTTP_BAD_REQUEST;
        }
        if (timestampValidator.validate(timestamp) && signatureValidator.validate(signature, timestamp, apiEvent.getBody())) {
            return HttpConstants.HTTP_OK;
        }
        return HttpConstants.HTTP_UNAUTHENTICATED;
    }
}
