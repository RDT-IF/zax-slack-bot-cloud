package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZaxBotRequestHandlerTest {
    private final ZaxBotRequestHandler handler = new ZaxBotRequestHandler();

    @Test
    void testThePlumbing() {
        APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody("{}").withHeaders(Collections.emptyMap());

        APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(apiEvent, new MockContext());

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }
}
