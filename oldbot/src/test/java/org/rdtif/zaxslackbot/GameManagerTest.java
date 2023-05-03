package org.rdtif.zaxslackbot;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.sameInstance;

class GameManagerTest {
    private final GameManager manager = GameManager.getInstance();

    @Test
    void findByIdNeverReturnsNull() {
        GameId id = new GameId();

        Optional<Game> game = manager.find(id);

        assertThat(game, notNullValue());
    }

    @Test
    void findProvidedGame() {
        Game expected = new Game(new GameId());
        manager.add(expected);

        Optional<Game> game = manager.find(expected.getId());

        assertThat(game, equalTo(Optional.of(expected)));
    }

    @Test
    void findProvidedGameReturnsEmptyWhenNotFound() {
        Game expected = new Game(new GameId());
        manager.add(expected);

        Optional<Game> game = manager.find(new GameId());

        assertThat(game, equalTo(Optional.empty()));
    }

    @Test
    void addMultiple() {
        Game expected1 = new Game(new GameId());
        Game expected2 = new Game(new GameId());
        Game expected3 = new Game(new GameId());

        manager.add(expected1, expected2, expected3);

        assertThat(manager.find(expected1.getId()), equalTo(Optional.of(expected1)));
        assertThat(manager.find(expected2.getId()), equalTo(Optional.of(expected2)));
        assertThat(manager.find(expected3.getId()), equalTo(Optional.of(expected3)));
    }

    @Test
    void getInstanceIsThreadSafe() throws Exception {
        List<GameManager> providers = gatherObjectsAsynchronously(this::getService);
        for (int i = 1; i < providers.size(); i++) {
            assertThat(providers.get(i - 1), sameInstance(providers.get(i)));
        }
    }

    private GameManager getService() {
        return GameManager.getInstance();
    }

    private <T> List<T> gatherObjectsAsynchronously(Callable<T> callable) throws Exception {
        Collection<Future<T>> futures = createThreads(callable);
        List<T> objects = new ArrayList<>();

        for (Future<T> future : futures) {
            objects.add(future.get());
        }
        return objects;
    }

    private <T> Collection<Future<T>> createThreads(Callable<T> callable) {
        Collection<Future<T>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            futures.add(executor.submit(callable));
        }
        return futures;
    }
}
