package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackEventHandlerTest {
    private final SlackEventDispatcher dispatcher = mock(SlackEventDispatcher.class);
    private final SlackEventValidator eventValidator = mock(SlackEventValidator.class);
    private final SlackEventHandler handler = new SlackEventHandler(eventValidator, dispatcher);

    @Test
    void handleValidEvent() {
        when(eventValidator.validate(any())).thenReturn(200);
        when(dispatcher.dispatch(any(), any())).thenReturn(HttpConstants.RESPONSE_OK);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent();

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }

    @Test
    void callDispatcherForValidEvent() {
        when(eventValidator.validate(any())).thenReturn(200);
        when(dispatcher.dispatch(any(), any())).thenReturn(HttpConstants.RESPONSE_OK);
        String body = "{}";

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(body);

        handler.handle(apiEvent);

        verify(dispatcher).dispatch(any(SlackEvent.class), eq(body));
    }

    @Test
    void invalidEvent() {
        when(eventValidator.validate(any())).thenReturn(400);
        when(dispatcher.dispatch(any(), any())).thenReturn(HttpConstants.RESPONSE_OK);

        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent();

        APIGatewayProxyResponseEvent responseEvent = handler.handle(apiEvent);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }
}
