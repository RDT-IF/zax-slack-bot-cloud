package org.rdtif.zaxslackbot;


import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ZaxSlackBotTest {
    @Test
    void startConnection() {
        SlackConnection connection = mock(SlackConnection.class);
        ZaxSlackBot bot = new ZaxSlackBot(connection);

        bot.run();

        verify(connection).connect();
    }
}
