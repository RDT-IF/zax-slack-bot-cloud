package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZaxBotRequestHandlerTest {
    private final ZaxBotRequestHandler handler = new ZaxBotRequestHandler();

    @Test
    void testThePlumbing() {
        APIGatewayProxyResponseEvent responseEvent = handler.handleRequest(new APIGatewayProxyRequestEvent(), null);

        assertThat(responseEvent.getStatusCode(), equalTo(400));
    }
}
