package org.rdtif.zaxslackbot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class LanguageProcessorTest {
    private final LanguageProcessor languageProcessor = new LanguageProcessor();

    @Test
    public void returnDefaultResponseWhenNoRegisteredPatterns() {
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguageProcessor.DEFAULT_MESSAGE));
    }

    @Test
    public void returnDefaultResponseWhenInputIsUnknown() {
        languageProcessor.registerPattern(createLanguagePattern("/doesnt/g", "matter"));
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguageProcessor.DEFAULT_MESSAGE));
    }

    @Test
    public void doNotRegisterNullPattern() {
        languageProcessor.registerPattern(null);
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguageProcessor.DEFAULT_MESSAGE));
    }

    @Test
    public void doNotRegisterLanguagePatternWhenNoPatternPresent() {
        languageProcessor.registerPattern(new LanguagePattern());
        assertThat(languageProcessor.responseTo(RandomStringUtils.randomAlphabetic(10)), equalTo(LanguageProcessor.DEFAULT_MESSAGE));
    }

    @Test
    public void caseInsensitiveMatching() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(input.toUpperCase(), response));

        assertThat(languageProcessor.responseTo(input.toLowerCase()), equalTo(response));
    }

    @Test
    public void returnResponseWhenInputMatchesPattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(input, response));

        assertThat(languageProcessor.responseTo(input), equalTo(response));
    }

    @Test
    public void returnRandomResponseFromMatchingPattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        String response2 = RandomStringUtils.randomAlphabetic(9);
        String response3 = RandomStringUtils.randomAlphabetic(7);
        languageProcessor.registerPattern(createLanguagePattern(input, response, response2, response3));

        boolean matchedResponse1 = false;
        boolean matchedResponse2 = false;
        boolean matchedResponse3 = false;

        for (int i = 0; i < 100;++i) {
            String result = languageProcessor.responseTo(input);

            if (!matchedResponse1 && Objects.equals(result, response)) {
                matchedResponse1 = true;
            } else if (!matchedResponse2 && Objects.equals(result, response2)) {
                matchedResponse2 = true;
            } else if (!matchedResponse3 && Objects.equals(result, response3)) {
                matchedResponse3 = true;
            }

            if (matchedResponse1 && matchedResponse2 && matchedResponse3) {
                break;
            }
        }

        assertThat(matchedResponse1 && matchedResponse2 && matchedResponse3, equalTo(true));
    }

    @Test
    public void matchCorrectLanguagePattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        languageProcessor.registerPattern(createLanguagePattern(RandomStringUtils.randomAlphanumeric(12), ""));
        languageProcessor.registerPattern(createLanguagePattern(input, response));

        assertThat(languageProcessor.responseTo(input), equalTo(response));
    }

    @Test
    public void returnDefaultResponseIfPatternHasNoResponses() {
        String input = RandomStringUtils.randomAlphabetic(10);
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(input);
        languageProcessor.registerPattern(languagePattern);

        assertThat(languageProcessor.responseTo(input), equalTo(LanguageProcessor.DEFAULT_MESSAGE));
    }

    private LanguagePattern createLanguagePattern(String pattern, String... response) {
        LanguagePattern languagePattern = new LanguagePattern();
        languagePattern.setPattern(pattern);

        LanguageResponse languageResponse = new LanguageResponse();
        languageResponse.setName("default");
        languageResponse.setResponses(Arrays.asList(response));
        languagePattern.setResponses(Collections.singletonList(languageResponse));

        return languagePattern;
    }
}