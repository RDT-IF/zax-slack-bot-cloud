package org.rdtif.zaxslackbot;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class GameManager {
    private static final Map<GameId, Game> games = new HashMap<>();
    private static final GameManager INSTANCE = new GameManager();

    private GameManager() {}

    static GameManager getInstance() {
        return INSTANCE;
    }

    Optional<Game> find(GameId id) {
        Game game = games.get(id);
        return game == null ? Optional.empty() : Optional.of(games.get(id));
    }

    void add(Game... games) {
        for (Game game : games) {
            GameManager.games.put(game.getId(), game);
        }
    }
}
