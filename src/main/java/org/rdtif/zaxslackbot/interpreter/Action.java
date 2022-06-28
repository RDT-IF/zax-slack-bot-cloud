package org.rdtif.zaxslackbot.interpreter;

public interface Action {
    String execute(String channelID, String input, LanguagePattern pattern);
}
