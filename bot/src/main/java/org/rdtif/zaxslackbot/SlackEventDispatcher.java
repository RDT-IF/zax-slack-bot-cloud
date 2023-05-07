package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import jakarta.inject.Inject;

class SlackEventDispatcher {
    private final Gson gson = new Gson();
    private final UrlVerificationHandler urlVerificationHandler;

    @Inject
    SlackEventDispatcher(UrlVerificationHandler urlVerificationHandler) {
        this.urlVerificationHandler = urlVerificationHandler;
    }

    APIGatewayProxyResponseEvent dispatch(SlackEvent event, String body) {
        switch (event.getType()) {
            case "url_verification":
                UrlVerificationEvent urlVerificationEvent = gson.fromJson(body, UrlVerificationEvent.class);
                return urlVerificationHandler.handle(urlVerificationEvent);
            case "app_mention":
            default:
                return HttpConstants.RESPONSE_BAD_REQUEST;
        }
    }
}
