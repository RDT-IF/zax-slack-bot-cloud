package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

class SlackEventDispatcher {
    private final Gson gson = new Gson();

    APIGatewayProxyResponseEvent dispatch(SlackEvent event, String body) {
        return HttpConstants.RESPONSE_OK;
//        switch (event.getType()) {
//            case "url_verification":
//                UrlVerificationEvent urlVerificationEvent = gson.fromJson(body, UrlVerificationEvent.class);
//                return new UrlVerificationHandler().handle(urlVerificationEvent);
//            default:
//                return StandardResponses.RESPONSE_FOR_INVALID_REQUEST;
//        }
    }
}
