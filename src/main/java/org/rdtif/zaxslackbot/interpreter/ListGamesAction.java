package org.rdtif.zaxslackbot.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.rdtif.zaxslackbot.GameRepository;

class ListGamesAction implements Action {
    private final GameRepository repository;

    ListGamesAction(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(String input, LanguagePattern pattern) {
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
            return names.stream().collect(Collectors.joining(", "));
        }
    }
}
