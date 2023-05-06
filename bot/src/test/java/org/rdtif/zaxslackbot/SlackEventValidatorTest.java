package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlackEventValidatorTest {
    private final SlackTimestampValidator timestampValidator = mock(SlackTimestampValidator.class);
    private final SlackSignatureValidator signatureValidator = mock(SlackSignatureValidator.class);
    private final SlackEventValidator validator = new SlackEventValidator(timestampValidator, signatureValidator);

    @Test
    void valid() {
        String body = "{\n    'challenge':'some challenge',    'type': 'url_verification'\n}";

        Map<String, String> headers = ImmutableMap.of("X-Slack-Request-Timestamp", "valid timestamp", "X-Slack-Signature", "valid signature");

        when(timestampValidator.validate("valid timestamp")).thenReturn(true);
        when(signatureValidator.validate("valid signature", "valid timestamp", body)).thenReturn(true);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body).withHeaders(headers);

        boolean validate = validator.validate(apiEvent);

        assertThat(validate, equalTo(true));
    }

    @Test
    void emptyBody() {
        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));

        boolean validate = validator.validate(apiEvent);

        assertThat(validate, equalTo(false));
    }

    @Test
    void missingType() {
        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);

        String body = "{\n    'challenge':'some challenge'\n}";
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body).withHeaders(Collections.singletonMap("X-Slack-Request-Timestamp", "valid timestamp"));

        boolean validate = validator.validate(apiEvent);

        assertThat(validate, equalTo(false));
    }

    @Test
    void invalidTimestamp() {
        when(timestampValidator.validate(anyString())).thenReturn(false);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(true);

        String body = "{\n    'challenge':'some challenge',    'type': 'url_verification'\n}";
        Map<String, String> headers = ImmutableMap.of("X-Slack-Request-Timestamp", "invalid timestamp", "X-Slack-Signature", "valid signature");

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body).withHeaders(headers);

        boolean validate = validator.validate(apiEvent);

        assertThat(validate, equalTo(false));
    }

    @Test
    void invalidSignature() {
        when(timestampValidator.validate(anyString())).thenReturn(true);
        when(signatureValidator.validate(anyString(), anyString(), anyString())).thenReturn(false);

        String body = "{\n    'challenge':'some challenge',    'type': 'url_verification'\n}";
        Map<String, String> headers = ImmutableMap.of("X-Slack-Request-Timestamp", "valid timestamp", "X-Slack-Signature", "invalid signature");

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body).withHeaders(headers);

        boolean validate = validator.validate(apiEvent);

        assertThat(validate, equalTo(false));
    }
}
