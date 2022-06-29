package org.rdtif.zaxslackbot.userinterface;

import org.rdtif.zaxslackbot.slack.SlackDisplayMessageMaker;
import org.rdtif.zaxslackbot.slack.SlackDisplayMessageUpdater;

public class SlackTextScreen extends TextScreen {
    private final SlackDisplayMessageUpdater updater;
    private final SlackDisplayMessageMaker maker;

    public SlackTextScreen(Extent size, SlackDisplayMessageUpdater updater, SlackDisplayMessageMaker maker) {
        super(size);
        this.updater = updater;
        this.maker = maker;
    }

    void initialize(int version) {
        super.setVersion(version);
        updater.initialize(maker.makeMessageOf("Display loading..."));
    }

    @Override
    void update() {
        updater.update(maker.makeMessageOf(getJoinedText()));
    }
}
