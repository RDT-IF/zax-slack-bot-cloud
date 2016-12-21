package org.rdtif.zaxslackbot;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ZaxSlackBotTest {
    @Test
    public void startConnection() {
        SlackConnection connection = mock(SlackConnection.class);
        ZaxSlackBot bot = new ZaxSlackBot(connection);

        bot.run();

        verify(connection).connect();
    }
}