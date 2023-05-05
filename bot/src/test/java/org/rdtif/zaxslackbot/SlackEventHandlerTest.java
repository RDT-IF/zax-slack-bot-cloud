package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlackEventHandlerTest {
    private final ChallengeValidator challengeValidator = mock(ChallengeValidator.class);
    private final SlackEventHandler handler = new SlackEventHandler(challengeValidator);

    @Test
    void handleEmptyPayload() {
        APIGatewayProxyResponseEvent responseEvent = handler.handle(null);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingType() {
        String body = "{\n" +
                "    'challenge': '3eZbrw1aBm2rZgRNFdxV2595E9CY3gmdALWMmHkvFXO7tYXAYM8P'\n" +
                "}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingChallenge() {
        String body = "{\n" +
                "    'type': 'url_verification'\n" +
                "}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }

    @Test
    void handleValidChallenge() {
        when(challengeValidator.valid("some valid challenge")).thenReturn(true);
        String body = "{\n"
                + "    'challenge':'some valid challenge',"
                + "    'type': 'url_verification'\n"
                + "}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }

    @Test
    void handleInvalidChallenge() {
        when(challengeValidator.valid(anyString())).thenReturn(false);
        String body = "{\n"
                + "    'challenge':'some invalid challenge',"
                + "    'type': 'url_verification'\n"
                + "}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }
}
