package org.rdtif.zaxslackbot.eventbus;

public record PostToChannelBusEvent(String channelID, String text) {}
