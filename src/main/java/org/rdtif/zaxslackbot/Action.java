package org.rdtif.zaxslackbot;

import java.util.List;

public interface Action {
    String execute(List<LanguageResponse> responses);
}
