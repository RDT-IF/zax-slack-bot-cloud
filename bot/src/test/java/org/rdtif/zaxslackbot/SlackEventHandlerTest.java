package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlackEventHandlerTest {
    private final SlackTimestampValidator slackTimestampValidator = mock(SlackTimestampValidator.class);
    private final SlackEventHandler handler = new SlackEventHandler(slackTimestampValidator);

    @Test
    void handleValidEvent() {
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";
        when(slackTimestampValidator.validate("valid timestamp")).thenReturn(true);
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }

    @Test
    void handleEmptyPayload() {
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));
        when(slackTimestampValidator.validate(anyString())).thenReturn(true);
        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingType() {
        String body = "{\n"
                + "    'challenge':'some challenge'\n"
                + "}";
        when(slackTimestampValidator.validate(anyString())).thenReturn(true);
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingTimestamp() {
        String body = "{\n"
                + "    'challenge':'some challenge',\n"
                + "    'type': 'url_verification'\n"
                + "}";
        when(slackTimestampValidator.validate(anyString())).thenReturn(true);
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(Collections.emptyMap());

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleInvalidEvent() {
        when(slackTimestampValidator.validate(anyString())).thenReturn(false);
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "invalid timestamp"));

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }
}
