package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

abstract class SlackEventHandler<EVENT_TYPE extends SlackEvent> {
    abstract APIGatewayProxyResponseEvent handle(EVENT_TYPE event);
}
