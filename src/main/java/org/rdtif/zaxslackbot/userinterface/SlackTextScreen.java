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
//2022-06-29 22:16:43: WARN : No BoltEventHandler registered for event: member_joined_channel
//---
//[Suggestion] You can handle this type of event with the following listener function:
//
//app.event(MemberJoinedChannelEvent.class, (payload, ctx) -> {
//  return ctx.ack();
//});
//
//2022-06-29 22:21:43: WARN : No BoltEventHandler registered for event: member_joined_channel
//---
//[Suggestion] You can handle this type of event with the following listener function:
//
//app.event(MemberJoinedChannelEvent.class, (payload, ctx) -> {
//  return ctx.ack();
//});