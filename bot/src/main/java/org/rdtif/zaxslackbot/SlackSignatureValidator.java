package org.rdtif.zaxslackbot;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import jakarta.inject.Inject;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

class SlackSignatureValidator {
    private final SecretKeySpec secretKeySpec;

    @Inject
    SlackSignatureValidator(SecretKeySpec secretKeySpec) {
        this.secretKeySpec = secretKeySpec;
    }

    boolean validate(String signature, String timestamp, String body) {
        String expectedOriginal = "v0:" + timestamp + ":" + body;
        @SuppressWarnings("UnstableApiUsage") HashFunction hashFunction = Hashing.hmacSha256(secretKeySpec);
        String expected = "v0=" + hashFunction.hashString(expectedOriginal, StandardCharsets.UTF_8);
        return expected.equals(signature);
    }
}
