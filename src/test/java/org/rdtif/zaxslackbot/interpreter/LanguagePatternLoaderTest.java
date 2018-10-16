package org.rdtif.zaxslackbot.interpreter;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LanguagePatternLoaderTest {
    private final LanguagePatternLoader loader = new LanguagePatternLoader();

    @Test
    void throwExceptionForFileNotFound() {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> loader.load(fileName));
        assertThat(exception.getCause(), instanceOf(FileNotFoundException.class));
        assertThat(exception.getMessage(), equalTo("java.io.FileNotFoundException: Pattern file '" + fileName + "' not found"));
    }

    @Test
    void loadAPattern() {
        LanguagePattern pattern = loader.load("GreetingPattern.json");

        assertThat(pattern.getAction(), equalTo(LanguageAction.Default));
    }
}
