package org.rdtif.zaxslackbot.userinterface;

import com.slack.api.model.block.LayoutBlock;
import org.junit.jupiter.api.Test;
import org.rdtif.zaxslackbot.slack.SlackDisplayMessageMaker;
import org.rdtif.zaxslackbot.slack.SlackDisplayMessageUpdater;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SlackTextScreenTest {
    private final SlackDisplayMessageUpdater mockUpdater = mock(SlackDisplayMessageUpdater.class);
    private final SlackDisplayMessageMaker displayMessageMaker = new SlackDisplayMessageMaker(new InputState());

    @Test
    void constructWithSize() {
        Extent size = new Extent(13, 17);
        SlackTextScreen screen = new SlackTextScreen(size, mockUpdater, displayMessageMaker);

        screen.initialize(5);

        assertThat(screen.getSize(), equalTo(size));
    }

    @Test
    void initializeVersion() {
        SlackTextScreen screen = new SlackTextScreen(new Extent(0, 0), mockUpdater, displayMessageMaker);

        screen.initialize(5);

        assertThat(screen.getVersion(), equalTo(5));
    }

    @Test
    void initializePostsMessage() {
        SlackTextScreen screen = new SlackTextScreen(new Extent(0, 0), mockUpdater, displayMessageMaker);
        List<LayoutBlock> message = new SlackDisplayMessageMaker(new InputState()).makeMessageOf("Display loading...");

        screen.initialize(5);

        verify(mockUpdater).initialize(message);
    }

    @Test
    void updateMessage() {
        SlackTextScreen screen = new SlackTextScreen(new Extent(10, 10), mockUpdater, displayMessageMaker);
        List<LayoutBlock> message = new SlackDisplayMessageMaker(new InputState()).makeMessageOf(" \n \n \n \n \n \n \n \n \n \n");

        screen.update();

        verify(mockUpdater).update(message);
    }
}
