package org.rdtif.zaxslackbot;

class Game {
    static final Game NOT_FOUND = new Game(new GameId());
    private final GameId id;

    Game(GameId id) {
        this.id = id;
    }

    GameId getId() {
        return id;
    }
}
