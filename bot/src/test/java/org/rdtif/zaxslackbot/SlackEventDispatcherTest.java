package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SlackEventDispatcherTest {
    @Test
    void urlVerificationEvent() {
        String body = "{ 'challenge':'a challenge'}";
        SlackEventDispatcher dispatcher = new SlackEventDispatcher(new UrlVerificationHandler());

        APIGatewayProxyResponseEvent responseEvent = dispatcher.dispatch(new SlackEvent("url_verification"), body);

        assertThat(responseEvent.getBody(), equalTo("a challenge"));
    }

    @Test
    void unknownEventType() {
        SlackEventDispatcher dispatcher = new SlackEventDispatcher(new UrlVerificationHandler());

        APIGatewayProxyResponseEvent responseEvent = dispatcher.dispatch(new SlackEvent("unknown type"), null);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }
}
