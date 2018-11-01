package org.rdtif.zaxslackbot.userinterface;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SlackTextScreenTest {
    private final SlackSession slackSession = mock(SlackSession.class);
    private final SlackChannel channel = mock(SlackChannel.class);
    private final SlackTextScreen textScreen = new SlackTextScreen(slackSession, channel, new Extent(1, 80));
    private final String INITIAL_TIMESTAMP = RandomStringUtils.randomNumeric(25);
    private final String LATER_TIMESTAMP = RandomStringUtils.randomNumeric(25);

    @BeforeEach
    void beforeEach() {
        @SuppressWarnings("unchecked") SlackMessageHandle<SlackMessageReply> initialHandle = mock(SlackMessageHandle.class);
        SlackMessageReply initialReply = mock(SlackMessageReply.class);
        when(slackSession.sendMessage(channel, "Display loading...")).thenReturn(initialHandle);
        when(initialHandle.getReply()).thenReturn(initialReply);
        when(initialReply.getTimestamp()).thenReturn(INITIAL_TIMESTAMP);

        @SuppressWarnings("unchecked") SlackMessageHandle<SlackMessageReply> laterHandle = mock(SlackMessageHandle.class);
        SlackMessageReply laterReply = mock(SlackMessageReply.class);
        when(slackSession.updateMessage(eq(INITIAL_TIMESTAMP), eq(channel), anyString())).thenReturn(laterHandle);
        when(laterHandle.getReply()).thenReturn(laterReply);
        when(laterReply.getTimestamp()).thenReturn(LATER_TIMESTAMP);
    }

    @Test
    void updateSendsMessage() {
        textScreen.initialize(0);

        textScreen.update();

        verify(slackSession).updateMessage(eq(INITIAL_TIMESTAMP), eq(channel), anyString());
    }

    @Test
    void wrapMessageInBackticks() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        textScreen.initialize(0);
        textScreen.print(line);

        textScreen.update();

        verify(slackSession).updateMessage(INITIAL_TIMESTAMP, channel, "```\n" + line + "\n\n```");
    }

    @Test
    void escapeTripleBackTicksInContent() {
        String line = "```";
        String expected = "`" + (char) 11 + "`" + (char) 11 + "`" + "\n";
        textScreen.initialize(0);
        textScreen.print(line);

        textScreen.update();

        verify(slackSession).updateMessage(INITIAL_TIMESTAMP, channel, "```\n" + expected + "\n```");
    }
}