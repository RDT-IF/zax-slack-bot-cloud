package org.rdtif.zaxslackbot;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.crypto.spec.SecretKeySpec;

class ZaxBotModule extends AbstractModule {
    private final ZaxLogger logger;

    public ZaxBotModule(ZaxLogger logger) {
        this.logger = logger;
    }

    @Override
    protected void configure() {
        bind(ZaxLogger.class).toInstance(logger);
    }

    @Provides
    SecretKeySpec providesKeySpec() {
        String signingSecret = System.getenv(SharedConstants.SIGNING_SECRET_ENVIRONMENT_VARIABLE);
        return new SecretKeySpec(signingSecret.getBytes(), "HmacSHA256");
    }
}
