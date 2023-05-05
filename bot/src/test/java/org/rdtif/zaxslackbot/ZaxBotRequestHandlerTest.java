package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZaxBotRequestHandlerTest {
    @Test
    void handlerReturns200() {
        APIGatewayProxyResponseEvent responseEvent = new ZaxBotRequestHandler().handleRequest(null, null);

        assertThat(responseEvent.getStatusCode(), equalTo(200));
    }
}
