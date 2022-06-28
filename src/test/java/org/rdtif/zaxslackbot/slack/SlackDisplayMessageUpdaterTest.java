package org.rdtif.zaxslackbot.slack;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.model.block.LayoutBlock;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackDisplayMessageUpdaterTest {
    private final SlackClient mockSlackClient = mock(SlackClient.class);
    private final SlackDisplayMessageUpdater updater = new SlackDisplayMessageUpdater(mockSlackClient, "channelID");

    @Test
    void handleInitialization() {
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        response.setTs("messageTimestamp");
        when(mockSlackClient.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(response);

        List<LayoutBlock> expectedContent = new SlackDisplayMessageMaker().makeMessageOf("some content");
        updater.initialize(expectedContent);
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("channelID")
                .blocks(expectedContent)
                .build();

        verify(mockSlackClient).chatPostMessage(request);
    }

    @Test
    void handleUpdate() {
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        response.setTs("messageTimestamp1");
        when(mockSlackClient.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(response);
        ChatUpdateResponse response2 = new ChatUpdateResponse();
        response2.setTs("messageTimestamp2");
        when(mockSlackClient.chatUpdate(any(ChatUpdateRequest.class))).thenReturn(response2);

        List<LayoutBlock> expectedContent = new SlackDisplayMessageMaker().makeMessageOf("some content");
        updater.initialize(new ArrayList<>());
        updater.update(expectedContent);
        ChatUpdateRequest request = ChatUpdateRequest.builder()
                .channel("channelID")
                .ts("messageTimestamp1")
                .blocks(expectedContent)
                .build();

        verify(mockSlackClient).chatUpdate(request);
    }

    @Test
    void handleSecondUpdate() {
        ChatPostMessageResponse response = new ChatPostMessageResponse();
        response.setTs("messageTimestamp1");
        when(mockSlackClient.chatPostMessage(any(ChatPostMessageRequest.class))).thenReturn(response);
        ChatUpdateResponse response2 = new ChatUpdateResponse();
        response2.setTs("messageTimestamp2");
        when(mockSlackClient.chatUpdate(any(ChatUpdateRequest.class))).thenReturn(response2);

        List<LayoutBlock> expectedContent = new SlackDisplayMessageMaker().makeMessageOf("some content");
        updater.initialize(new ArrayList<>());
        updater.update(expectedContent);
        ChatUpdateRequest request2 = ChatUpdateRequest.builder()
                .channel("channelID")
                .ts("messageTimestamp2")
                .blocks(expectedContent)
                .build();

        updater.update(expectedContent);

        verify(mockSlackClient).chatUpdate(request2);
    }
}
