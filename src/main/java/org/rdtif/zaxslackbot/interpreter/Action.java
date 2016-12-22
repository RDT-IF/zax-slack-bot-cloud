package org.rdtif.zaxslackbot.interpreter;

public interface Action {
    String execute(String input, LanguagePattern pattern);
}
