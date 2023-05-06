package org.rdtif.zaxslackbot;

import jakarta.inject.Inject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

class SlackSignatureValidator {
    private final SecretKeySpec secretKeySpec;

    @Inject
    SlackSignatureValidator(SecretKeySpec secretKeySpec) {
        this.secretKeySpec = secretKeySpec;
    }

    boolean validate(String signature, String timestamp, String body) {
        String signatureOriginal = "v0:" + timestamp + ":" + body;
        try {
            Mac hmacSHA256 = Mac.getInstance(secretKeySpec.getAlgorithm());
            hmacSHA256.init(secretKeySpec);
            String expected = Arrays.toString(hmacSHA256.doFinal(signatureOriginal.getBytes()));
            return expected.equals(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException exception) {
            return false;
        }
    }
}
