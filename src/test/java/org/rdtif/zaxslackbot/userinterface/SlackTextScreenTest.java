package org.rdtif.zaxslackbot.userinterface;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.replies.SlackMessageReply;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class SlackTextScreenTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    private final SlackSession slackSession = mock(SlackSession.class);
    private final SlackChannel channel = mock(SlackChannel.class);
    private final SlackTextScreen textScreen = new SlackTextScreen(slackSession, channel, new Extent(1, 80));
    private final String INITIAL_TIMESTAMP = RandomStringUtils.randomNumeric(25);
    private final String LATER_TIMESTAMP = RandomStringUtils.randomNumeric(25);

    @Before
    public void beforeEach() {
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
    public void updateSendsMessage() {
        textScreen.initialize();

        textScreen.update();

        verify(slackSession).updateMessage(eq(INITIAL_TIMESTAMP), eq(channel), anyString());
    }

    @Test
    public void wrapMessageInBackticks() {
        String line = RandomStringUtils.randomAlphanumeric(25);
        textScreen.initialize();
        textScreen.print(line);

        textScreen.update();

        verify(slackSession).updateMessage(INITIAL_TIMESTAMP, channel, "```\n" + line + "\n\n```");
    }

    @Test
    public void escapeTripleBackTicksInContent() {
        String line = "```";
        String expected = "`" + (char) 11 + "`" + (char) 11 + "`" + "\n";
        textScreen.initialize();
        textScreen.print(line);

        textScreen.update();

        verify(slackSession).updateMessage(INITIAL_TIMESTAMP, channel, "```\n" + expected + "\n```");
    }
}