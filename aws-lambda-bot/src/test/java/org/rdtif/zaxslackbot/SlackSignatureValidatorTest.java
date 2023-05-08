package org.rdtif.zaxslackbot;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class SlackSignatureValidatorTest {
    private static final SecretKeySpec TEST_KEY_SPEC = new SecretKeySpec("TEST_SIGNING_SECRET".getBytes(), "HmacSHA256");
    private final SlackSignatureValidator validator = new SlackSignatureValidator(TEST_KEY_SPEC);

    @Test
    void validSignature() {
        HashFunction hashFunction = Hashing.hmacSha256(TEST_KEY_SPEC);
        String signature = "v0=" + hashFunction.hashString("v0:timestamp:body", StandardCharsets.UTF_8);

        boolean validate = validator.validate(signature, "timestamp", "body");

        assertThat(validate, equalTo(true));
    }

    @Test
    void invalidSignature() {
        boolean validate = validator.validate("something is wrong here", "", "");

        assertThat(validate, equalTo(false));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void nullOrEmptySignature(String signature) {
        boolean validate = validator.validate(signature, "", "");

        assertThat(validate, equalTo(false));
    }
}
