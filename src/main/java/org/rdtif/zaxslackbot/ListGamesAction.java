package org.rdtif.zaxslackbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

class ListGamesAction implements Action {
    private final GameRepository repository;

    ListGamesAction(GameRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<LanguageResponse> responses) {
        List<String> names = new ArrayList<>(repository.fileNames());
        if (names.size() == 0) {
            return getByName(responses, "default");
        } else {
            return getByName(responses, "games") + formatList(names) + ".";
        }
    }

    private String getByName(List<LanguageResponse> responses, String name) {
        Optional<LanguageResponse> first = responses.stream().filter(response -> name.equalsIgnoreCase("default")).findFirst();
        if (first.isPresent()) {
            LanguageResponse response = first.get();
            return response.getResponses().get(new Random().nextInt(response.getResponses().size()));
        }
        return "";
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