package org.rdtif.zaxslackbot.interpreter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class LanguagePatternTest {
    @Test
    public void patternDefaultsToEmpty() {
        LanguagePattern pattern = new LanguagePattern();

        assertThat(pattern.getPattern(), isEmptyString());
    }

    @Test
    public void defaultAction() {
        LanguagePattern pattern = new LanguagePattern();

        assertThat(pattern.getAction(), equalTo(LanguageAction.Default));
    }

    @Test
    public void responseForShouldNeverReturnNull() {
        LanguagePattern pattern = new LanguagePattern();

        String languageResponse = pattern.responseFor(RandomStringUtils.randomAlphabetic(12));

        assertThat(languageResponse, notNullValue());
    }

    @Test
    public void returnLanguageResponseForName() {
        String name = RandomStringUtils.randomAlphabetic(13);
        String expected = RandomStringUtils.randomAlphabetic(13);
        LanguagePattern pattern = createLanguagePattern(createLanguageResponse(name, expected));

        String languageResponse = pattern.responseFor(name);

        assertThat(languageResponse, equalTo(expected));
    }

    @Test
    public void returnLanguageResponseForNameWithoutResponses() {
        String name = RandomStringUtils.randomAlphabetic(13);
        LanguagePattern pattern = createLanguagePattern(name);

        String languageResponse = pattern.responseFor(name);

        assertThat(languageResponse, equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    public void returnEmptyResponseForNonExistentName() {
        LanguageResponse expected = new LanguageResponse();
        expected.setName(RandomStringUtils.randomAlphabetic(13));
        LanguagePattern pattern = createLanguagePattern(expected);

        String languageResponse = pattern.responseFor(RandomStringUtils.randomAlphabetic(12));

        assertThat(languageResponse, equalTo(LanguagePattern.DEFAULT_MESSAGE));
    }

    @Test
    public void returnRandomResponseFromMatchingPattern() {
        String input = RandomStringUtils.randomAlphabetic(10);
        String response = RandomStringUtils.randomAlphabetic(8);
        String response2 = RandomStringUtils.randomAlphabetic(9);
        String response3 = RandomStringUtils.randomAlphabetic(7);
        LanguagePattern pattern = createLanguagePattern(input, response, response2, response3);

        boolean matchedResponse1 = false;
        boolean matchedResponse2 = false;
        boolean matchedResponse3 = false;

        for (int i = 0; i < 100;++i) {
            String result = pattern.responseFor("default");

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

    private LanguagePattern createLanguagePattern(LanguageResponse response) {
        LanguagePattern pattern = new LanguagePattern();
        pattern.setResponses(Collections.singletonList(response));
        return pattern;
    }

    private LanguageResponse createLanguageResponse(String name, String expected) {
        LanguageResponse response = new LanguageResponse();
        response.setResponses(Collections.singletonList(expected));
        response.setName(name);
        return response;
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
