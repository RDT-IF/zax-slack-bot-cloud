package org.rdtif.zaxslackbot;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class UrlVerificationHandlerTest {
    private final UrlVerificationHandler handler = new UrlVerificationHandler();

    @Test
    void returnResponse200() {
        UrlVerificationEvent event = new UrlVerificationEvent("challenge", "url_verification");

        APIGatewayProxyResponseEvent response = handler.handle(event);

        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    void returnResponseWithChallenge() {
        UrlVerificationEvent event = new UrlVerificationEvent("challenge", "url_verification");

        APIGatewayProxyResponseEvent response = handler.handle(event);

        assertThat(response.getBody(), equalTo("challenge"));
    }

    @Test
    void returnResponseWithContentType() {
        UrlVerificationEvent event = new UrlVerificationEvent("challenge", "url_verification");

        APIGatewayProxyResponseEvent response = handler.handle(event);

        assertThat(response.getHeaders().get("Content-Type"), equalTo("text/plain"));
    }
}
