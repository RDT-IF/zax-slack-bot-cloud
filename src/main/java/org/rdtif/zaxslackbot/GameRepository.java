package org.rdtif.zaxslackbot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.inject.Inject;

public class GameRepository {
    private static final Predicate<Path> FOR_REGULAR_FILES = (path -> Files.isRegularFile(path));
    private static final Predicate<String> ENDS_WITH_Z_VERSION = Pattern.compile(".*\\.z[1-5|7-8]$").asPredicate();
    private final ZaxSlackBotConfiguration configuration;

    @Inject
    GameRepository(ZaxSlackBotConfiguration configuration) {
        this.configuration = configuration;
    }

    public List<String> fileNames() {
        try (Stream<Path> paths = Files.walk(Paths.get(configuration.getGameDirectory()))) {
            return paths.filter(FOR_REGULAR_FILES).map(Path::getFileName).map(Path::toString).filter(ENDS_WITH_Z_VERSION).collect(Collectors.toList());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
