package org.rdtif.zaxslackbot;

class Game {
    private final GameId id;

    Game(GameId id) {
        this.id = id;
    }

    GameId getId() {
        return id;
    }
}
