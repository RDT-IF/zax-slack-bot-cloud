package org.rdtif.zaxslackbot.eventbus;

public record AppMentionBusEvent(String channelID, String text) {}
