package org.rdtif.zaxslackbot;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collections;
import java.util.Optional;

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

        Optional<LanguageResponse> languageResponse = pattern.responseFor(RandomStringUtils.randomAlphabetic(12));

        assertThat(languageResponse, notNullValue());
    }
    @Test
    public void returnLanguageResponseForName() {
        String name = RandomStringUtils.randomAlphabetic(13);
        LanguageResponse expected = new LanguageResponse();
        expected.setName(name);
        LanguagePattern pattern = new LanguagePattern();
        pattern.setResponses(Collections.singletonList(expected));

        Optional<LanguageResponse> languageResponse = pattern.responseFor(name);

        assertThat(languageResponse.isPresent(), equalTo(true));
    }

    @Test
    public void returnEmptyResponseForNonExistentName() {
        LanguageResponse expected = new LanguageResponse();
        expected.setName(RandomStringUtils.randomAlphabetic(13));
        LanguagePattern pattern = new LanguagePattern();
        pattern.setResponses(Collections.singletonList(expected));

        Optional<LanguageResponse> languageResponse = pattern.responseFor(RandomStringUtils.randomAlphabetic(12));

        assertThat(languageResponse.isPresent(), equalTo(false));
    }
}
