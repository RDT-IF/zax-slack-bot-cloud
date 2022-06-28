package org.rdtif.zaxslackbot.slack;

import com.slack.api.bolt.jetty.SlackAppServer;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SlackApplicationServerTest {
    @Test
    void start() throws Exception {
        SlackAppServer mockServer = mock(SlackAppServer.class);
        SlackApplicationServer server = new SlackApplicationServer(mockServer);

        server.start();

        verify(mockServer).start();
    }
}
