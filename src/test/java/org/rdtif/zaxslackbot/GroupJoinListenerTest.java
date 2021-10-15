package org.rdtif.zaxslackbot;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackEventType;
import com.ullink.slack.simpleslackapi.events.SlackGroupJoined;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GroupJoinListenerTest {
    private final GroupJoinListener listener = new GroupJoinListener();

    @Test
    void verifyGreeting() {
        SlackSession session = mock(SlackSession.class);
        SlackChannel channel = mock(SlackChannel.class);
        GroupJoinedEvent event = new GroupJoinedEvent(channel);


        listener.onEvent(event, session);

        verify(session).sendMessage(channel, "Hi Everyone! Would you like to play a game?");
    }

    private static class GroupJoinedEvent extends SlackGroupJoined {
        private final SlackChannel channel;

        GroupJoinedEvent(SlackChannel channel) {
            super(channel);
            this.channel = channel;
        }

        @Override
        public SlackChannel getSlackChannel() {
            return channel;
        }

        @Override
        public SlackEventType getEventType() {
            return null;
        }
    }

}
