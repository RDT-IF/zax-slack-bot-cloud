package org.rdtif.zaxslackbot.slack;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.model.block.LayoutBlock;

import javax.inject.Inject;
import java.util.List;

public class SlackDisplayMessageUpdater {
    private final SlackClient slackClient;
    private final String channelID;
    private String slackMessageTimestamp;

    @Inject
    public SlackDisplayMessageUpdater(SlackClient slackClient, String channelID) {
        this.slackClient = slackClient;
        this.channelID = channelID;
    }

    public void initialize(List<LayoutBlock> content) {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel(channelID)
                .text("foo")
                .blocks(content)
                .build();
        ChatPostMessageResponse response = slackClient.chatPostMessage(request);
        slackMessageTimestamp = response.getTs();
    }

    public void update(List<LayoutBlock> content) {
        ChatUpdateRequest request = ChatUpdateRequest.builder()
                .channel(channelID)
                .ts(slackMessageTimestamp)
                .text("foo")
                .blocks(content)
                .build();
        ChatUpdateResponse response = slackClient.chatUpdate(request);
        slackMessageTimestamp = response.getTs();
    }
}
