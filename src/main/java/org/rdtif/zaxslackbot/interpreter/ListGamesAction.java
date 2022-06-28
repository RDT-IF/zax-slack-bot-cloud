package org.rdtif.zaxslackbot.interpreter;

import org.rdtif.zaxslackbot.GameFileRepository;

import java.util.ArrayList;
import java.util.List;

class ListGamesAction implements Action {
    private final GameFileRepository repository;

    ListGamesAction(GameFileRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(String channelID, String input, LanguagePattern pattern) {
        List<String> names = new ArrayList<>(repository.fileNames());
        if (names.size() == 0) {
            return pattern.responseFor("default");
        } else {
            return pattern.responseFor("games") + formatList(names) + ".";
        }
    }

    private String formatList(List<String> names) {
        if (names.size() == 1) {
            return names.get(0);
        } else if (names.size() == 2) {
            return names.get(0) + " and " + names.get(1);
        } else {
            String last = "and " + names.remove(names.size() - 1);
            names.add(last);
            return String.join(", ", names);
        }
    }
}
