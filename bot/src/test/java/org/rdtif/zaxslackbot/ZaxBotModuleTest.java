package org.rdtif.zaxslackbot;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ZaxBotModuleTest {
    @Test
    void specifyHmacSHA256() {
        Injector injector = Guice.createInjector(new ZaxBotModule());
        SecretKeySpec keySpec = injector.getInstance(SecretKeySpec.class);

        assertThat(keySpec.getAlgorithm(), equalTo("HmacSHA256"));
    }

    @Test
    void useKeyFromEnvironment() throws Exception {
        Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
        hmacSHA256.init(new SecretKeySpec(System.getenv("ZAXBOT_SLACK_SIGNING_SECRET").getBytes(), "HmacSHA256"));
        byte[] expected = hmacSHA256.doFinal("test string".getBytes());

        Injector injector = Guice.createInjector(new ZaxBotModule());
        SecretKeySpec keySpec = injector.getInstance(SecretKeySpec.class);
        hmacSHA256.init(keySpec);
        byte[] actual = hmacSHA256.doFinal("test string".getBytes());

        assertThat(actual, equalTo(expected));

    }
}
