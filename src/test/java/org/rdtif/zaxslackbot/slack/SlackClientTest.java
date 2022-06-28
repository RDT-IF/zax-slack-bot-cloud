package org.rdtif.zaxslackbot.slack;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackClientTest {
    private final MethodsClient mockMethodsClient = mock(MethodsClient.class);
    private final Provider<MethodsClient> methodsClientProvider = () -> mockMethodsClient;

    @Test
    void postMessageDelegation() throws Exception {
        ChatPostMessageResponse expectedResponse = new ChatPostMessageResponse();
        when(mockMethodsClient.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(expectedResponse);
        SlackClient slackClient = new SlackClient(methodsClientProvider);
        ChatPostMessageRequest request = ChatPostMessageRequest.builder().build();

        ChatPostMessageResponse response = slackClient.chatPostMessage(request);

        verify(mockMethodsClient).chatPostMessage(request);
        assertThat(response, sameInstance(expectedResponse));
    }

    @Test
    void updateMessageDelegation() throws Exception {
        ChatUpdateResponse expectedResponse = new ChatUpdateResponse();
        when(mockMethodsClient.chatUpdate(any(ChatUpdateRequest.class))).thenReturn(expectedResponse);
        SlackClient slackClient = new SlackClient(methodsClientProvider);
        ChatUpdateRequest request = ChatUpdateRequest.builder().build();

        ChatUpdateResponse response = slackClient.chatUpdate(request);

        verify(mockMethodsClient).chatUpdate(request);
        assertThat(response, sameInstance(expectedResponse));
    }
}
