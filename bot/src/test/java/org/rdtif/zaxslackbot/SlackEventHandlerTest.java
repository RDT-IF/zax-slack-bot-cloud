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
    private final SlackEventValidator eventValidator = mock(SlackEventValidator.class);
    private final SlackEventHandler handler = new SlackEventHandler(timestampValidator, signatureValidator, dispatcher, eventValidator);

    @Test
    void handleValidEvent() {
        when(eventValidator.validate(any())).thenReturn(true);
        when(dispatcher.dispatch(any(), any())).thenReturn(StandardResponses.RESPONSE_FOR_VALID_REQUEST);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent();

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }

    @Test
    void callDispatcherForValidEvent() {
        when(eventValidator.validate(any())).thenReturn(true);
        when(dispatcher.dispatch(any(), any())).thenReturn(StandardResponses.RESPONSE_FOR_VALID_REQUEST);
        String body = "{}";

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        handler.handle(apiEvent);

        verify(dispatcher).dispatch(any(SlackEvent.class), eq(body));
    }

    @Test
    void invalidEvent() {
        when(eventValidator.validate(any())).thenReturn(false);
        when(dispatcher.dispatch(any(), any())).thenReturn(StandardResponses.RESPONSE_FOR_VALID_REQUEST);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent();

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(401));
    }
}
