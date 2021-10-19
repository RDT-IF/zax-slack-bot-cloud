package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.GenericSlackReply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
class SlackDisplayMessageUpdaterTest {
    private static final String RESPONSE_JSON = "{\n" +
            "    \"ok\": true,\n" +
            "    \"channel\": \"channelID\",\n" +
            "    \"ts\": \"timestamp\",\n" +
            "    \"text\": \"text\",\n" +
            "    \"message\": {\n" +
            "        \"text\": \"text\",\n" +
            "        \"user\": \"userID\"\n" +
            "    }\n" +
            "}";
    private final SlackSession slackSession = mock(SlackSession.class);
    private final SlackDisplayMessageUpdater poster = new SlackDisplayMessageUpdater(slackSession, "channelID");
    private final SlackMessageHandle<GenericSlackReply> handle = new SlackMessageHandle<>(0);

    @BeforeEach
    void beforeEach() {
        GenericSlackReply reply = new GenericSlackReply(RESPONSE_JSON);
        handle.setReply(reply);
        when(slackSession.postGenericSlackCommand(any(), any())).thenReturn(handle);
    }

    @Test
    void postPosts() {
        poster.postMessage("");

        verify(slackSession).postGenericSlackCommand(any(), eq("chat.postMessage"));
    }

    @Test
    void postUsesChannelID() {
        poster.postMessage("");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        assertThat(parameters.getValue().get("channel"), equalTo("channelID"));
    }

    @Test
    void postHandlesStrangeResponse() {
        SlackMessageHandle<GenericSlackReply> handle = new SlackMessageHandle<>(0);
        handle.setReply(new GenericSlackReply(""));
        when(slackSession.postGenericSlackCommand(any(), any())).thenReturn(handle);

        String timestamp = poster.postMessage("");
        assertThat(timestamp, emptyString());
    }

    @Test
    void postUsesContent() {
        poster.postMessage("some content");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        assertThat(parameters.getValue().get("blocks"), equalTo(blockWith("```\nsome content\n```")));
    }

    @Test
    void postEscapesBackticksInContent() {
        poster.postMessage("some``` content");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String processedContent = "```\n" + "some``` content".replaceAll("```", replaceActualBackTicks) + "\n```";
        assertThat(parameters.getValue().get("blocks"), equalTo(blockWith(processedContent)));
    }

    @Test
    void updateUpdates() {
        poster.updateMessage("");

        verify(slackSession).postGenericSlackCommand(any(), eq("chat.update"));
    }

    @Test
    void updateUsesChannelID() {
        poster.updateMessage("");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        assertThat(parameters.getValue().get("channel"), equalTo("channelID"));
    }

    @Test
    void updateUsesMessageTimestamp() {
        poster.postMessage("");
        poster.updateMessage("");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession, times(2)).postGenericSlackCommand(parameters.capture(), any(String.class));
        assertThat(parameters.getValue().get("ts"), equalTo("timestamp"));
    }

    @Test
    void updateUsesContent() {
        poster.updateMessage("some content");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        assertThat(parameters.getValue().get("blocks"), equalTo(blockWith("```\nsome content\n```")));
    }

    @Test
    void updateEscapesBackticksInContent() {
        poster.updateMessage("some``` content");

        ArgumentCaptor<Map<String, String>> parameters = ArgumentCaptor.forClass(Map.class);
        verify(slackSession).postGenericSlackCommand(parameters.capture(), any(String.class));
        String replaceActualBackTicks = "`" + (char) 11 + "`" + (char) 11 + "`";
        String processedContent = "```\n" + "some``` content".replaceAll("```", replaceActualBackTicks) + "\n```";
        assertThat(parameters.getValue().get("blocks"), equalTo(blockWith(processedContent)));
    }

    @Test
    void updateHandlesStrangeResponse() {
        SlackMessageHandle<GenericSlackReply> handle = new SlackMessageHandle<>(0);
        handle.setReply(new GenericSlackReply(""));
        when(slackSession.postGenericSlackCommand(any(), any())).thenReturn(handle);

        String timestamp = poster.updateMessage("");
        assertThat(timestamp, emptyString());
    }

    private String blockWith(String content) {
        return "[" +
                "  {" +
                "    \"type\": \"section\"," +
                "    \"text\": {" +
                "      \"type\": \"mrkdwn\"," +
                "      \"text\": \"" + content + "\"" +
                "    }" +
                "  }," +
                "  {" +
                "    \"type\": \"input\"," +
                "    \"block_id\": \"input123\"," +
                "    \"label\": {" +
                "      \"type\": \"plain_text\"," +
                "      \"text\": \" \"" +
                "    }," +
                "    \"element\": {" +
                "      \"type\": \"plain_text_input\"," +
                "      \"action_id\": \"plain_input\"," +
                "      \"placeholder\": {" +
                "        \"type\": \"plain_text\"," +
                "        \"text\": \"Enter some plain text\"" +
                "      }" +
                "    }" +
                "  }" +
                "]";
    }
}
