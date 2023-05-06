package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackEventHandlerTest {
    private final SlackTimestampValidator timestampValidator = mock(SlackTimestampValidator.class);
    private final SlackSignatureValidator signatureValidator = mock(SlackSignatureValidator.class);
    private final SlackEventDispatcher dispatcher = mock(SlackEventDispatcher.class);
    private final SlackEventHandler handler = new SlackEventHandler(timestampValidator, signatureValidator, dispatcher);

    @Test
    void handleValidEvent() {
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";

        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Request-Timestamp", "valid timestamp",
                "X-Slack-Signature", "valid signature");

        when(timestampValidator.validate("valid timestamp")).thenReturn(true);
        when(signatureValidator.validate("valid signature", "valid timestamp", body)).thenReturn(true);
        when(dispatcher.dispatch(any(), any())).thenReturn(StandardResponses.RESPONSE_FOR_VALID_REQUEST);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }

    @Test
    void callDispatcherForValidEvent() {
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";

        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Request-Timestamp", "valid timestamp",
                "X-Slack-Signature", "valid signature");

        when(timestampValidator.validate("valid timestamp")).thenReturn(true);
        when(signatureValidator.validate("valid signature", "valid timestamp", body)).thenReturn(true);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        verify(dispatcher).dispatch(any(SlackEvent.class), eq(body));
    }

    @Test
    void handleInvalidByTimestampEvent() {
        when(timestampValidator.validate(anyString())).thenReturn(false);
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";
        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Request-Timestamp", "invalid timestamp",
                "X-Slack-Signature", "valid signature");

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }

    @Test
    void handleInvalidBySignatureEvent() {
        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(false);
        String body = "{\n"
                + "    'challenge':'some challenge',"
                + "    'type': 'url_verification'\n"
                + "}";

        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Request-Timestamp", "valid timestamp",
                "X-Slack-Signature", "invalid signature");

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }

    @Test
    void handleEmptyPayload() {
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));
        when(timestampValidator.validate(anyString())).thenReturn(true);
        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingType() {
        String body = "{\n"
                + "    'challenge':'some challenge'\n"
                + "}";
        when(timestampValidator.validate(anyString())).thenReturn(true);
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
        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Signature", "valid signature");

        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers);

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }

    @Test
    void handleMissingSignature() {
        String body = "{\n"
                + "    'challenge':'some challenge',\n"
                + "    'type': 'url_verification'\n"
                + "}";
        Map<String, String> headers = ImmutableMap.of(
                "X-Slack-Request-Timestamp", "valid timestamp");

        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(Collections.emptyMap());

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }
}
