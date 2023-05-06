package org.rdtif.zaxslackbot;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.crypto.spec.SecretKeySpec;

class ZaxBotModule extends AbstractModule {
    @Provides
    SecretKeySpec providesKeySpec() {
        String signingSecret = System.getenv(SharedConstants.SIGNING_SECRET_ENVIRONMENT_VARIABLE);
        return new SecretKeySpec(signingSecret.getBytes(), "HmacSHA256");
    }
}
