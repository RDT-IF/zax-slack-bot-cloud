package org.rdtif.zaxslackbot.userinterface;

import org.rdtif.zaxslackbot.slack.SlackDisplayMessageMaker;

public class SlackTextScreen extends TextScreen {
    private final org.rdtif.zaxslackbot.slack.SlackDisplayMessageUpdater updater;
    private final SlackDisplayMessageMaker maker = new SlackDisplayMessageMaker();

    public SlackTextScreen(Extent size, org.rdtif.zaxslackbot.slack.SlackDisplayMessageUpdater updater) {
        super(size);
        this.updater = updater;
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
