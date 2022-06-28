package org.rdtif.zaxslackbot.slack;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.IOException;

public class SlackClient {
    private final Provider<MethodsClient> methodsClient;

    @Inject
    public SlackClient(Provider<MethodsClient> methodsClient) {
        this.methodsClient = methodsClient;
    }

    public ChatPostMessageResponse chatPostMessage(ChatPostMessageRequest request) {
        try {
            return methodsClient.get().chatPostMessage(request);
        } catch (IOException | SlackApiException exception) {
            throw new RuntimeException(exception);
        }
    }

    public ChatUpdateResponse chatUpdate(ChatUpdateRequest request) {
        try {
            return methodsClient.get().chatUpdate(request);
        } catch (IOException | SlackApiException exception) {
            throw new RuntimeException(exception);
        }
    }
}
