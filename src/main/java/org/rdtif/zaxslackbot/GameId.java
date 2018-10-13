package org.rdtif.zaxslackbot;

import java.util.Objects;
import java.util.UUID;

class GameId {
    private final UUID uuid;

    GameId() {
        uuid = UUID.randomUUID();
    }

    @Override
    public final boolean equals(Object other) {
        if (other instanceof GameId) {
            GameId otherGameId = (GameId) other;
            return Objects.equals(uuid, otherGameId.uuid);
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(uuid);
    }
}
