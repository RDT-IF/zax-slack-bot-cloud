package org.rdtif.zaxslackbot.interpreter;

import com.ullink.slack.simpleslackapi.SlackChannel;

public interface Action {
    String execute(SlackChannel channel, String input, LanguagePattern pattern);
}
